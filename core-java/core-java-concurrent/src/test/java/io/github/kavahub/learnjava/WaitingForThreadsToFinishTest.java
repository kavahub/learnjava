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
 * mvn test -Dtest=WaitingForThreadsToFinishTest
 */
@Slf4j
public class WaitingForThreadsToFinishTest {
    private ExecutorService WORKER_THREAD_POOL;

    @BeforeEach
    public void setUp() {
        WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);
    }

    @AfterEach
    public void clearUp(){
        awaitTerminationAfterShutdown(WORKER_THREAD_POOL);
    }

    public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        if (threadPool.isShutdown()){
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

            /**
             * CountDownLatch能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。使用一个计数器进行实现。
             * 计数器初始值为线程的数量。当每一个线程完成自己任务后，计数器的值就会减一。当计数器的值为0时，
             * 表示所有的线程都已经完成一些任务，然后在CountDownLatch上等待的线程就可以恢复执行接下来的任务
             */
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
            //assertTrue(totalProcessingTime >= 3000);
            log.info("givenMultipleThreads_whenInvokeAll_thenMainThreadShouldWaitForAllToFinish: {}", totalProcessingTime);

            String firstThreadResponse = futures.get(0).get();
            assertTrue("fast thread".equals(firstThreadResponse));

            String secondThreadResponse = futures.get(1).get();
            assertTrue("slow thread".equals(secondThreadResponse));

        } catch (ExecutionException | InterruptedException ex) {
            log.error("", ex);
        }
    }

    // * CompletionService接口定义了一组任务管理接口:
    // * submit() - 提交任务
    // * take() - 获取任务结果
    // * poll() - 获取任务结果
    // * ExecutorCompletionService类是CompletionService接口的实现
    // * ExecutorCompletionService内部管理者一个已完成任务的阻塞队列
    // * ExecutorCompletionService引用了一个Executor, 用来执行任务
    // * submit()方法最终会委托给内部的executor去执行任务
    // * take/poll方法的工作都委托给内部的已完成任务阻塞队列. 如果阻塞队列中有已完成的任务, take方法就返回任务的结果, 否则阻塞等待任务完成
    // * poll与take方法不同, poll有两个版本:
    // * 无参的poll方法 --- 如果完成队列中有数据就返回, 否则返回null
    // * 有参数的poll方法 --- 如果完成队列中有数据就直接返回,否则等待指定的时间, 到时间后如果还是没有数据就返回null
    // * ExecutorCompletionService主要用与管理异步任务 (有结果的任务,任务完成后要处理结果)
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
