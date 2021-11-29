package io.github.kavahub.learnjava.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link MoreExecutors} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MoreExecutorsTest {
    @Test
    public void whenExecutingRunnableInThreadPool_shouldLogAllThreadsExecutions() throws Exception {
        ConcurrentHashMap<String, Boolean> threadExecutions = new ConcurrentHashMap<>();

        Runnable logThreadRun = () -> threadExecutions.put(Thread.currentThread().getName(), true);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(logThreadRun);
        executorService.submit(logThreadRun);

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        assertEquals(threadExecutions.size(), 2);
        //assertTrue(threadExecutions.get("pool-1-thread-1"));
        //assertTrue(threadExecutions.get("pool-1-thread-2"));
    }

    @Test
    public void whenExecutingRunnableInDirectExecutor_shouldLogThreadExecution() throws Exception {
        ConcurrentHashMap<String, Boolean> threadExecutions = new ConcurrentHashMap<>();

        Runnable logThreadRun = () -> threadExecutions.put(Thread.currentThread().getName(), true);

        // 创建一个直接执行的Executor，调用execute()会在当前线程执行Runnable的方法
        Executor executor = MoreExecutors.directExecutor();
        executor.execute(logThreadRun);

        assertTrue(threadExecutions.get("main"));
    }

    @Test
    public void whenExecutingRunnableInListeningExecutor_shouldLogThreadExecution() throws Exception {
        ConcurrentHashMap<String, Boolean> threadExecutions = new ConcurrentHashMap<>();

        Runnable logThreadRun = () -> threadExecutions.put(Thread.currentThread().getName(), true);

        // 创建一个直接执行的线程池，线程池里面所有的任务在提交的时候就执行
        ListeningExecutorService executor = MoreExecutors.newDirectExecutorService();
        executor.execute(logThreadRun);
        executor.execute(logThreadRun);
        
        assertTrue(threadExecutions.get("main"));
    }   
}
