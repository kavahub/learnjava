package io.github.kavahub.learnjava.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

import com.google.common.util.concurrent.Striped;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @formatter::off 性能影响因素： 1. 锁代码块逻辑太简单，体现不了锁的优势 2. 添加了workTime方法后，性能测试正常 3.
 *                 调整workTime方法中的worktime的值，观察性能变化
 * 
 *                 Benchmark Mode Cnt Score Error Units
 *                 ConcurrentAccessBenchmark.singleLockConcurrentHashMap thrpt
 *                 10 0.093 ± 0.001 ops/ms
 *                 ConcurrentAccessBenchmark.singleLockHashMap thrpt 10 0.089 ±
 *                 0.011 ops/ms
 *                 ConcurrentAccessBenchmark.stripedLockConcurrentHashMap thrpt
 *                 10 0.072 ± 0.001 ops/ms
 *                 ConcurrentAccessBenchmark.stripedLockHashMap thrpt 10 0.075 ±
 *                 0.004 ops/ms
 * 
 * @formatter::on
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
@State(Scope.Thread)
public class ConcurrentAccessBenchmark {
    static final int SLOTS = 4;
    static final int THREADS = 10000;
    static final int BUCKETS = Runtime.getRuntime().availableProcessors() * SLOTS;
    SingleLock singleLock = new SingleLock();
    StripedLock stripedLock = new StripedLock(BUCKETS);

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder().include(ConcurrentAccessBenchmark.class.getSimpleName()).build();

        new Runner(opts).run();
    }

    @Benchmark
    public Map<String, String> singleLockHashMap() throws InterruptedException {
        return singleLock.doWork(new HashMap<String, String>(), THREADS, SLOTS);
    }

    @Benchmark
    public Map<String, String> stripedLockHashMap() throws InterruptedException {
        return stripedLock.doWork(new HashMap<String, String>(), THREADS, SLOTS);
    }

    @Benchmark
    public Map<String, String> singleLockConcurrentHashMap() throws InterruptedException {
        return singleLock.doWork(new ConcurrentHashMap<String, String>(), THREADS, SLOTS);
    }

    @Benchmark
    public Map<String, String> stripedLockConcurrentHashMap() throws InterruptedException {
        return stripedLock.doWork(new ConcurrentHashMap<String, String>(), THREADS, SLOTS);
    }

    /**
     * @formatter:off
     * Future模式是多线程设计常用的一种设计模式。Future模式可以理解成：我有一个任务，
     * 提交给了Future，Future替我完成这个任务。期间我自己可以去做任何想做的事情。 一段时间之后，我就便可以从Future那儿取出结果。 
     * 缺点: 
     * 1. Future虽然可以实现获取异步执行结果的需求，但是它没有提供通知的机制，我们无法得知Future什么时候完成。 
     * 2. 要么使用阻塞，在future.get()的地方等待future返回的结果，这时又变成同步操作。要么使用isDone()轮询地判断Future是否完成，这样会耗费CPU的资源。
     * 
     * CompletableFuture能够将回调放到与任务不同的线程中执行，也能将回调作为继续执行的同步函数，在与任务相同的线程中执行。它避免了传统回调最大的问题，
     * 那就是能够将控制流分离到不同的事件处理器中。
     * 
     * CompletableFuture弥补了Future模式的缺点。在异步的任务完成后，需要用其结果继续操作时，无需等待。
     * 可以直接通过thenAccept、thenApply、thenCompose等方式将前面异步处理的结果交给另外一个异步事件处理线程来处理。
     * @formatter:on
     * 
     * 
     */
    public abstract class ConcurrentAccessExperiment {

        public final Map<String, String> doWork(Map<String, String> map, int threads, int slots) {
            CompletableFuture<?>[] requests = new CompletableFuture<?>[threads * slots];

            for (int i = 0; i < threads; i++) {
                requests[slots * i + 0] = CompletableFuture.supplyAsync(putSupplier(map, i));
                requests[slots * i + 1] = CompletableFuture.supplyAsync(getSupplier(map, i));
                requests[slots * i + 2] = CompletableFuture.supplyAsync(getSupplier(map, i));
                requests[slots * i + 3] = CompletableFuture.supplyAsync(getSupplier(map, i));
            }
            CompletableFuture.allOf(requests).join();

            return map;
        }

        protected abstract Supplier<?> putSupplier(Map<String, String> map, int key);

        protected abstract Supplier<?> getSupplier(Map<String, String> map, int key);

        /**
         * 耗时的业务逻辑
         */
        protected void workTime() {
            // 单位：纳秒
            // final long worktime = 5;
            final long worktime = 100;
            final long now = System.nanoTime();
            while (System.nanoTime() <= now + worktime) {

            }
        }
    }

    /**
     * ReentrantLock(可重入锁)
     * 
     * @formatter:off
     * 
     * 与Synchronized的区别：
     * 
     * 1. 对于Synchronized来说，它是java语言的关键字，是原生语法层面的互斥，需要jvm实现。而ReentrantLock它是JDK 1.5之后提供的API层面的互斥锁，
     * 需要lock()和unlock()方法配合try/finally语句块来完成。
     * 
     * 2. synchronized是不可中断类型的锁，除非加锁的代码中出现异常或正常执行完成； ReentrantLock则可以中断，
     * 可通过trylock(long timeout,TimeUnit unit)设置超时方法或者将lockInterruptibly()放到代码块中，调用interrupt方法进行中断
     * 
     * 3. synchronized为非公平锁， ReentrantLock则即可以选公平锁也可以选非公平锁
     * 
     * 4. synchronized不能绑定； ReentrantLock通过绑定Condition结合await()/singal()方法实现线程的精确唤醒，
     * 而不是像synchronized通过Object类的wait()/notify()/notifyAll()方法要么随机唤醒一个线程要么唤醒全部线程
     * 
     * 5. synchronzied锁的是对象，锁是保存在对象头里面的，根据对象头数据来标识是否有线程获得锁/争抢锁；
     * ReentrantLock锁的是线程，根据进入的线程和int类型的state标识锁的获得/争抢
     * 
     * @formatter:on
     */
    public class SingleLock extends ConcurrentAccessExperiment {
        ReentrantLock lock;

        public SingleLock() {
            lock = new ReentrantLock();
        }

        protected Supplier<?> putSupplier(Map<String, String> map, int key) {
            return (() -> {
                lock.lock();
                try {
                    this.workTime();
                    return map.put("key" + key, "value" + key);
                } finally {
                    lock.unlock();
                }
            });
        }

        protected Supplier<?> getSupplier(Map<String, String> map, int key) {
            return (() -> {
                lock.lock();
                try {
                    this.workTime();
                    return map.get("key" + key);
                } finally {
                    lock.unlock();
                }
            });
        }

    }

    public class StripedLock extends ConcurrentAccessExperiment {
        final Striped<Lock> stripedLock;
        final int stripedSize;

        public StripedLock(int buckets) {
            stripedLock = Striped.lock(buckets);
            stripedSize = stripedLock.size();
        }

        protected Supplier<?> putSupplier(Map<String, String> map, int key) {
            return (() -> {
                int bucket = key % stripedSize;
                Lock lock = stripedLock.get(bucket);
                lock.lock();
                try {
                    this.workTime();
                    return map.put("key" + key, "value" + key);
                } finally {
                    lock.unlock();
                }
            });
        }

        protected Supplier<?> getSupplier(Map<String, String> map, int key) {
            return (() -> {
                int bucket = key % stripedSize;
                Lock lock = stripedLock.get(bucket);
                lock.lock();
                try {
                    this.workTime();
                    return map.get("key" + key);
                } finally {
                    lock.unlock();
                }
            });
        }

    }

}
