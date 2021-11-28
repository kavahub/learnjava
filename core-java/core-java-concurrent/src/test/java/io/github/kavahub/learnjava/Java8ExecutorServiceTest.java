package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link ExecutorService} 示例
 *
 * <p>
 * 线程池创建注意事项：
 * <ul>
 * <li> 当线程池小于corePoolSize时，新提交任务将创建一个新线程执行任务，即使此时线程池中存在空闲线程</li>
 * <li> 当线程池达到corePoolSize时，新提交任务将被放入workQueue中，等待线程池中任务调度执行</li>
 * <li> 当workQueue已满，且maximumPoolSize>corePoolSize时，新提交任务会创建新线程执行任务</li>
 * <li> 当提交任务数超过maximumPoolSize时，新提交任务由RejectedExecutionHandler处理</li>
 * <li> 当线程池中超过corePoolSize线程，空闲时间达到keepAliveTime时，关闭空闲线程</li>
 * <li> 当设置allowCoreThreadTimeOut(true)时，线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭</li>
 * </ul>
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class Java8ExecutorServiceTest {
    private Runnable runnableTask;
    private Callable<String> callableTaskOutput;
    private Callable<String> callableTask;
    private List<Callable<String>> callableTasks;

    @BeforeEach
    public void init() {

        runnableTask = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        callableTaskOutput = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " - interrupted !");
            }
            return "Task's execution";
        };

        callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(300);
            return "Task's execution";
        };

        callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
    }

    @Test
    public void creationSubmittingTaskShuttingDown_whenShutDown_thenCorrect() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(runnableTask);
        executorService.submit(callableTask);
        executorService.shutdown();

        assertTrue(executorService.isShutdown());
    }

    @Test
    public void creationSubmittingTasksShuttingDownNow_whenShutDownAfterAwating_thenCorrect() {
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());

        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.submit(callableTaskOutput);
        }

        List<Runnable> notExecutedTasks = smartShutdown(threadPoolExecutor);

        assertTrue(threadPoolExecutor.isShutdown());
        assertFalse(notExecutedTasks.isEmpty());
        assertTrue(notExecutedTasks.size() < 98);
    }

    private List<Runnable> smartShutdown(ExecutorService executorService) {

        List<Runnable> notExecutedTasks = new ArrayList<>();
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                notExecutedTasks = executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            notExecutedTasks = executorService.shutdownNow();
        }
        return notExecutedTasks;
    }

    @Test
    public void submittingTasks_whenExecutedOneAndAll_thenCorrect() {
        // newFixedThreadPool方法创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        String result = null;
        List<Future<String>> futures = new ArrayList<>();
        try {
            // invokeAny()取得第一个完成任务的结果值，当第一个任务执行完成后，会调用interrupt()方法将其他任务中断
            result = executorService.invokeAny(callableTasks);
            // invokeAll() 等全部线程任务执行完毕后，取得全部完成任务的结果值
            futures = executorService.invokeAll(callableTasks);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals("Task's execution", result);
        assertTrue(futures.size() == 3);

        executorService.shutdown();
    }

    @Test
    public void submittingTaskShuttingDown_whenGetExpectedResult_thenCorrect() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<String> future = executorService.submit(callableTask);
        String result = null;
        try {
            result = future.get();
            result = future.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        assertEquals("Task's execution", result);
    }

    @Test
    public void submittingTask_whenCanceled_thenCorrect() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<String> future = executorService.submit(() -> {
            System.out.println("run");
            return "run";
        });
        // 未调用get方法，所以任务未运行
        boolean canceled = future.cancel(true);
        boolean isCancelled = future.isCancelled();

        executorService.shutdown();

        assertTrue(canceled);
        assertTrue(isCancelled);
    }

    @Test
    public void submittingTaskScheduling_whenExecuted_thenCorrect() {
        // 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        Future<String> resultFuture = executorService.schedule(callableTask, 1, TimeUnit.SECONDS);
        String result = null;
        try {
            result = resultFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        assertEquals("Task's execution", result);
    }
}
