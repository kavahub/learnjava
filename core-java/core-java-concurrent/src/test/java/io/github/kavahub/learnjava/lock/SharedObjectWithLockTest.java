package io.github.kavahub.learnjava.lock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link SharedObjectWithLock} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SharedObjectWithLockTest {
    @Test
    public void whenLockAcquired_ThenLockedIsTrue() throws InterruptedException {
        final SharedObjectWithLock object = new SharedObjectWithLock();

        final int threadCount = 2;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);

        executePerformThreads(object, threadCount, service);
        TimeUnit.MILLISECONDS.sleep(80);
        
        assertEquals(true, object.isLocked());

        service.shutdown();
    }

    @Test
    public void whenLocked_ThenQueuedThread() throws InterruptedException {
        final int threadCount = 4;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final SharedObjectWithLock object = new SharedObjectWithLock();

        executePerformThreads(object, threadCount, service);
        TimeUnit.MILLISECONDS.sleep(100);
        
        assertEquals(object.hasQueuedThreads(), true);

        service.shutdown();

    }

    @Test
    public void whenTryLock_ThenQueuedThread() throws InterruptedException {
        final SharedObjectWithLock object = new SharedObjectWithLock();

        final int threadCount = 2;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);

        executePerformTryLockThreads(object, threadCount, service);
        TimeUnit.MILLISECONDS.sleep(100);
        
        assertEquals(true, object.isLocked());

        service.shutdown();
    }

    @Test
    public void whenGetCount_ThenCorrectCount() throws InterruptedException {
        final int threadCount = 4;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final SharedObjectWithLock object = new SharedObjectWithLock();

        executePerformThreads(object, threadCount, service);
        Thread.sleep(1000);

        assertEquals(4, object.getCounter());

        service.shutdown();

    }

    private void executePerformThreads(SharedObjectWithLock object, int threadCount, ExecutorService service) {
        for (int i = 0; i < threadCount; i++) {
            service.execute(object::perform);
        }
    }

    private void executePerformTryLockThreads(SharedObjectWithLock object, int threadCount, ExecutorService service) {
        for (int i = 0; i < threadCount; i++) {
            service.execute(object::performTryLock);
        }
    }
}
