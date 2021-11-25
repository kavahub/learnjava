package io.github.kavahub.learnjava.counter;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class CounterLogicalTest {
    private static ExecutorService EXECUTOR;

    @BeforeAll
    public static void setUp() {
        EXECUTOR = Executors.newFixedThreadPool(2);
    }
    
    @AfterAll
    public static void clearUp() {
        EXECUTOR.shutdown();
    }

    @Test
    public void whenObjectLockCounter_whenSubmit_thenCorrect() throws Exception {
        ObjectLockCounter counter = new ObjectLockCounter();
        Future<Integer> future1 = (Future<Integer>) EXECUTOR.submit(new ObjectLockCounterCallable(counter));
        Future<Integer> future2 = (Future<Integer>) EXECUTOR.submit(new ObjectLockCounterCallable(counter));

        // Just to make sure both are completed
        future1.get();
        future2.get();

        assertThat(counter.getCounter()).isEqualTo(2);
    }

    @Test
    public void givenReentrantLockCounter_whenSubmit_thenCorrect() throws Exception {
        ReentrantLockCounter counter = new ReentrantLockCounter();
        Future<Integer> future1 = (Future<Integer>) EXECUTOR.submit(new ReentrantLockCounterCallable(counter));
        Future<Integer> future2 = (Future<Integer>) EXECUTOR.submit(new ReentrantLockCounterCallable(counter));

        // Just to make sure both are completed
        future1.get();
        future2.get();

        assertThat(counter.getCounter()).isEqualTo(2);
    }

    @Test
    public void givenReentrantReadWriteLockCounter_whenSubmit_thenCorrect() throws Exception {
        ReentrantReadWriteLockCounter counter = new ReentrantReadWriteLockCounter();
        Future<Integer> future1 = (Future<Integer>) EXECUTOR.submit(new ReentranReadWriteLockCounterCallable(counter));
        Future<Integer> future2 = (Future<Integer>) EXECUTOR.submit(new ReentranReadWriteLockCounterCallable(counter));

        // Just to make sure both are completed
        future1.get();
        future2.get();

        assertThat(counter.getCounter()).isEqualTo(2);
    }

    @Test
    public void givenCounter_whenSubmit_thenCorrect() throws Exception {
        Counter counter = new Counter();
        Future<Integer> future1 = (Future<Integer>) EXECUTOR.submit(new CounterCallable(counter));
        Future<Integer> future2 = (Future<Integer>) EXECUTOR.submit(new CounterCallable(counter));

        // Just to make sure both are completed
        future1.get();
        future2.get();

        assertThat(counter.getCounter()).isEqualTo(2);
    }

    @Test
    public void givenSemaphoreCounter_whenSubmit_thenCorrect() throws Exception {
        SemaphoreCounter counter = new SemaphoreCounter();
        Future<Integer> future1 = (Future<Integer>) EXECUTOR.submit(new SemaphoreCounterCallable(counter));
        Future<Integer> future2 = (Future<Integer>) EXECUTOR.submit(new SemaphoreCounterCallable(counter));

        // Just to make sure both are completed
        future1.get();
        future2.get();

        assertThat(counter.getCounter()).isEqualTo(2);
    }


    @Test
    public void givenNonBlockCounter_whenSubmit_thenCorrect() throws Exception {
        NonBlockCounter counter = new NonBlockCounter();
        Future<Integer> future1 = (Future<Integer>) EXECUTOR.submit(new NonBlockCounterCallable(counter));
        Future<Integer> future2 = (Future<Integer>) EXECUTOR.submit(new NonBlockCounterCallable(counter));

        // Just to make sure both are completed
        future1.get();
        future2.get();

        assertThat(counter.getCounter()).isEqualTo(2);
    }

    @Test
    public void givenMessageService_whenSubmit_thenCorrect() throws Exception {
        MessageService messageService = new MessageService("Hello world!");
        Future<String> future1 = (Future<String>) EXECUTOR.submit(new MessageServiceCallable(messageService));
        Future<String> future2 = (Future<String>) EXECUTOR.submit(new MessageServiceCallable(messageService));

        assertThat(future1.get()).isEqualTo("Hello world!");
        assertThat(future2.get()).isEqualTo("Hello world!");
    }

    @Test
    public void whenCalledFactorialMethod_thenCorrect() {
        assertThat(MathUtils.factorial(2)).isEqualTo(new BigInteger("2"));
    }
}
