package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

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
 * 性能测试结果如下：
 * 
 * <pre>
 * Benchmark                                                     Mode  Cnt     Score     Error  Units
 * FairnessBoundedBlockingQueueBenchmarker.V2                   thrpt   10  3926.104 ± 869.521  ops/s
 * FairnessBoundedBlockingQueueBenchmarker.V3                   thrpt   10  3531.130 ± 278.957  ops/s
 * FairnessBoundedBlockingQueueBenchmarker.V4                   thrpt   10  3624.021 ±  89.659  ops/s
 * FairnessBoundedBlockingQueueBenchmarker.linkedBlockingQueue  thrpt   10  5373.745 ±  82.383  ops/s
 * FairnessBoundedBlockingQueueBenchmarker.synchronousQueue     thrpt   10  5112.596 ± 334.031  ops/s
 * </pre>
 * 
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
@State(Scope.Thread)
public class FairnessBoundedBlockingQueueBenchmarker {
    private final static int queueSize = 10;
    private final static int loopCount = queueSize * 10;

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder().include(FairnessBoundedBlockingQueueBenchmarker.class.getSimpleName())
        .shouldFailOnError(true).build();

        new Runner(opts).run();
    }

    @Benchmark
    public void V2() throws InterruptedException {
        FairnessBoundedBlockingQueueV2<Integer> queue = new FairnessBoundedBlockingQueueV2<Integer>(queueSize);
        Thread t1 = new Thread(new Producer(queue, loopCount));
        Thread t2 = new Thread(new Consumer(queue, loopCount));
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
    }

    @Benchmark
    public void V3() throws InterruptedException {
        FairnessBoundedBlockingQueueV3<Integer> queue = new FairnessBoundedBlockingQueueV3<Integer>(queueSize);
        Thread t1 = new Thread(new Producer(queue, loopCount));
        Thread t2 = new Thread(new Consumer(queue, loopCount));
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
    }

    @Benchmark
    public void V4() throws InterruptedException {
        FairnessBoundedBlockingQueueV4<Integer> queue = new FairnessBoundedBlockingQueueV4<Integer>(queueSize);
        Thread t1 = new Thread(new Producer(queue, loopCount));
        Thread t2 = new Thread(new Consumer(queue, loopCount));
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
    }

    @Benchmark
    public void linkedBlockingQueue() throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(queueSize);
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < loopCount; i++) {
                queue.offer(i);
            }  
        });
        Thread t2 = new Thread(() -> {
            for(int i = 0; i < loopCount; i++) {
                queue.poll();
            }  
        });
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
    }

    @Benchmark
    public void synchronousQueue() throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < loopCount; i++) {
                queue.offer(i);
            }  
        });
        Thread t2 = new Thread(() -> {
            for(int i = 0; i < loopCount; i++) {
                queue.poll();
            }  
        });
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
    }

    static class Producer implements Runnable {
        private final Queue<Integer> queue;
        private final int loopCount;
        
        public Producer(Queue<Integer> queue, int loopCount) {
            this.queue = queue;
            this.loopCount = loopCount;
        }

        @Override
        public void run() {
            for(int i = 0; i < loopCount; i++) {
                queue.offer(i);
            }    
        }

    }

    static class Consumer implements Runnable {
        private final Queue<Integer> queue;
        private final int loopCount;
        
        public Consumer(Queue<Integer> queue, int loopCount) {
            this.queue = queue;
            this.loopCount = loopCount;
        }

        @Override
        public void run() {
            for(int i = 0; i < loopCount; i++) {
                queue.poll();
            }  
        }

    }
}
