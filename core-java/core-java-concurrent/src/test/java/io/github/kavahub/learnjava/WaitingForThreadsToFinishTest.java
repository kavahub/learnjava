package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 等待线程完成示例
 * 
 * <p>
 * {@link CompletionService} 与 {@code ExecutorService}
 * 类似都可以用来执行线程池的任务，{@code ExecutorService} 继承了 {@code Executor}
 * 接口，而 {@code CompletionService} 则是一个接口
 * 
 * <p>
 * 实际上是 {@code CompletionService} 和 {@code BlockingQueue}
 * 的结合体，{@code CompletionService} 用来提交任务，而 {@code BlockingQueue}
 * 用来保存封装成 {@code Future} 的执行结果。通过调用 {@code CompletionService#take()} 和
 * {@code CompletionService#poll()} 方法来获取到{@code Future}
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class WaitingForThreadsToFinishTest {
    private ExecutorService WORKER_THREAD_POOL;

    @BeforeEach
    public void setUp() {
        WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);
    }

    @AfterEach
    public void clearUp() {
        awaitTerminationAfterShutdown(WORKER_THREAD_POOL);
    }

    public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        if (threadPool.isShutdown()) {
            return;
        }
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void givenMultipleThreads_whenUsingCountDownLatch_thenMainShoudWaitForAllToFinish() {
        try {
            long startTime = System.currentTimeMillis();

            // create a CountDownLatch that waits for the 2 threads to finish
            CountDownLatch latch = new CountDownLatch(2);

            for (int i = 0; i < 2; i++) {
                WORKER_THREAD_POOL.submit(() -> {
                    try {
                        Thread.sleep(1000);
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                });
            }

            // wait for the latch to be decremented by the two threads
            latch.await();

            long processingTime = System.currentTimeMillis() - startTime;
            assertTrue(processingTime >= 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        awaitTerminationAfterShutdown(WORKER_THREAD_POOL);
    }

    @Test
    public void givenMultipleThreads_whenInvokeAll_thenMainThreadShouldWaitForAllToFinish() {
        List<Callable<String>> callables = Arrays.asList(new DelayedCallable("fast thread", 100),
                new DelayedCallable("slow thread", 3000));

        try {
            long startProcessingTime = System.currentTimeMillis();
            List<Future<String>> futures = WORKER_THREAD_POOL.invokeAll(callables);

            awaitTerminationAfterShutdown(WORKER_THREAD_POOL);

            try {
                // 异常，线程池已经关闭
                WORKER_THREAD_POOL.submit((Callable<String>) () -> {
                    Thread.sleep(1000000);
                    return null;
                });
            } catch (RejectedExecutionException ex) {
                //
            }

            long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;
            // 偶尔测试失败，整个项目测试时
            // assertTrue(totalProcessingTime >= 3000);
            log.info("givenMultipleThreads_whenInvokeAll_thenMainThreadShouldWaitForAllToFinish: {}",
                    totalProcessingTime);

            String firstThreadResponse = futures.get(0).get();
            assertTrue("fast thread".equals(firstThreadResponse));

            String secondThreadResponse = futures.get(1).get();
            assertTrue("slow thread".equals(secondThreadResponse));

        } catch (ExecutionException | InterruptedException ex) {
            log.error("", ex);
        }
    }

    @Test
    public void givenMultipleThreads_whenUsingCompletionService_thenMainThreadShouldWaitForAllToFinish() {
        CompletionService<String> service = new ExecutorCompletionService<>(WORKER_THREAD_POOL);

        List<Callable<String>> callables = Arrays.asList(new DelayedCallable("fast thread", 100),
                new DelayedCallable("slow thread", 3000));

        for (Callable<String> callable : callables) {
            service.submit(callable);
        }

        try {

            long startProcessingTime = System.currentTimeMillis();

            Future<String> future = service.take();
            String firstThreadResponse = future.get();
            long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;

            assertTrue("fast thread".equals(firstThreadResponse));
            assertTrue(totalProcessingTime >= 100 && totalProcessingTime < 1000);
            log.debug("Thread finished after: " + totalProcessingTime + " milliseconds");

            future = service.take();
            String secondThreadResponse = future.get();
            totalProcessingTime = System.currentTimeMillis() - startProcessingTime;

            assertTrue("slow thread".equals(secondThreadResponse));
            assertTrue(totalProcessingTime >= 3000 && totalProcessingTime < 4000);
            log.debug("Thread finished after: " + totalProcessingTime + " milliseconds");

        } catch (ExecutionException | InterruptedException ex) {
            log.error("", ex);
        } finally {
            awaitTerminationAfterShutdown(WORKER_THREAD_POOL);
        }
    }

    public static class DelayedCallable implements Callable<String> {

        private String name;
        private long period;
        private CountDownLatch latch;

        public DelayedCallable(String name, long period, CountDownLatch latch) {
            this(name, period);
            this.latch = latch;
        }

        public DelayedCallable(String name, long period) {
            this.name = name;
            this.period = period;
        }

        public String call() {
            System.out.println("DelayedCallable run");
            try {
                Thread.sleep(period);

                if (latch != null) {
                    latch.countDown();
                }

            } catch (InterruptedException ex) {
                // handle exception
                ex.printStackTrace();
                Thread.currentThread().interrupt();
            }

            return name;
        }
    }
}
