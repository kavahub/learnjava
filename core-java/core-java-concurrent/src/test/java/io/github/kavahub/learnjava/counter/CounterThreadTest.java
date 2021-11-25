package io.github.kavahub.learnjava.counter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 * 多数测试返回值的数量是不相等的，除{@see CounterThreadTest#givenReentrantLockCounter()}外，
 * 这是因为XXXXCallable类中的call方法中的代码没有同步，
 * 更好的方式是加1后返回值，参考{@see ReentrantLockCounter#incrementCounter()}。
 */
public class CounterThreadTest {
    private final static int THREADS = 1000;

    private final Set<Integer> doWork(Callable<Integer> callable, int threads) {
        Set<Integer> reslut = ConcurrentHashMap.newKeySet(threads);
        CompletableFuture<?>[] requests = new CompletableFuture<?>[threads];

        for (int i = 0; i < threads; i++) {
            requests[i] = CompletableFuture.runAsync(() -> {
                try {
                    reslut.add(callable.call());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
        CompletableFuture.allOf(requests).join();

        return reslut;
    }

    @Test
    @Disabled
    public void givenObjectLockCounter() throws Exception {
        ObjectLockCounter counter = new ObjectLockCounter();
        ObjectLockCounterCallable callable = new ObjectLockCounterCallable(counter);

        Set<Integer> countSet = this.doWork(callable, THREADS);

        assertThat(counter.getCounter()).isEqualTo(THREADS);
        assertThat(countSet.size()).isEqualTo(THREADS);
    }

    @Test
    public void givenReentrantLockCounter() throws Exception {
        ReentrantLockCounter counter = new ReentrantLockCounter();
        ReentrantLockCounterCallable callable = new ReentrantLockCounterCallable(counter);

        Set<Integer> countSet = this.doWork(callable, THREADS);

        assertThat(counter.getCounter()).isEqualTo(THREADS);
        assertThat(countSet.size()).isEqualTo(THREADS);
    }

    @Test
    @Disabled
    public void givenReentrantReadWriteLockCounter() throws Exception {
        ReentrantReadWriteLockCounter counter = new ReentrantReadWriteLockCounter();
        ReentranReadWriteLockCounterCallable callable = new ReentranReadWriteLockCounterCallable(counter);

        Set<Integer> countSet = this.doWork(callable, THREADS);

        assertThat(counter.getCounter()).isEqualTo(THREADS);
        assertThat(countSet.size()).isEqualTo(THREADS);

    }

    @Test
    @Disabled
    public void givenCounter_whenSubmit_thenCorrect() throws Exception {
        Counter counter = new Counter();
        CounterCallable callable = new CounterCallable(counter);

        Set<Integer> countSet = this.doWork(callable, THREADS);

        assertThat(counter.getCounter()).isEqualTo(THREADS);
        assertThat(countSet.size()).isEqualTo(THREADS);
    }

    @Test
    @Disabled
    public void givenSemaphoreCounter_whenSubmit_thenCorrect() throws Exception {
        SemaphoreCounter counter = new SemaphoreCounter();
        SemaphoreCounterCallable callable = new SemaphoreCounterCallable(counter);

        Set<Integer> countSet = this.doWork(callable, THREADS);

        assertThat(counter.getCounter()).isEqualTo(THREADS);
        assertThat(countSet.size()).isEqualTo(THREADS);
    }

    // NonBlockCounter

    @Test
    @Disabled
    public void givenNonBlockCounter_whenSubmit_thenCorrect() throws Exception {
        NonBlockCounter counter = new NonBlockCounter();
        NonBlockCounterCallable callable = new NonBlockCounterCallable(counter);

        Set<Integer> countSet = this.doWork(callable, THREADS);

        assertThat(counter.getCounter()).isEqualTo(THREADS);
        assertThat(countSet.size()).isEqualTo(THREADS);
    }

    @Test
    public void givenMultiThread_whenSafeCounterWithLockIncrement() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        NonBlockCounter safeCounter = new NonBlockCounter();

        IntStream.range(0, 1000)
          .forEach(count -> service.submit(safeCounter::incrementCounter));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertEquals(1000, safeCounter.getCounter());
    }
    
    @Test
    public void givenMultiThread_whenSafeCounterWithoutLockIncrement() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        NonBlockCounter safeCounter = new NonBlockCounter();

        IntStream.range(0, 1000)
          .forEach(count -> service.submit(safeCounter::incrementCounter));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertEquals(1000, safeCounter.getCounter());
    }

    // SemaphoreCounter
    
    @Test
    public void givenMultiThread_whenMutexAndMultipleThreads_thenBlocked() throws InterruptedException {
        final int count = 5;
        final ExecutorService executorService = Executors.newFixedThreadPool(count);
        final SemaphoreCounter counter = new SemaphoreCounter();
        IntStream.range(0, count).forEach(user -> executorService.submit(() -> {
            try {
                System.out.println("start");
                counter.incrementCounter();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }));
        TimeUnit.MILLISECONDS.sleep(100);
        assertTrue(counter.hasQueuedThreads());

        // 没有输出时间毫秒，是因为在sleep时就中断了
        executorService.shutdown();
    }

    @Test
    public void givenMultiThread__ThenDelay_thenCorrectCount() throws InterruptedException {
        final int count = 5;
        final ExecutorService executorService = Executors.newFixedThreadPool(count);
        final SemaphoreCounter counter = new SemaphoreCounter();
        IntStream.range(0, count).forEach(user -> executorService.execute(() -> {
            try {
                counter.incrementCounter();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }));

        TimeUnit.MILLISECONDS.sleep(100);

        assertTrue(counter.hasQueuedThreads());
        TimeUnit.SECONDS.sleep(5);
        assertFalse(counter.hasQueuedThreads());
        assertEquals(count, counter.getCounter());

        executorService.shutdown();
    }

    // UnsafeCounter

    @Test
    public void givenMultiThread_whenUnsafeCounterIncrement() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        UnsafeCounter unsafeCounter = new UnsafeCounter();

        IntStream.range(0, 1000)
          .forEach(count -> service.submit(unsafeCounter::incrementCounter));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertThat(unsafeCounter.getCounter()).isLessThan(1000);
    }
}
