package io.github.kavahub.learnjava.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SharedObjectWithLock {
    private ReentrantLock lock = new ReentrantLock(true);

    // 共享对象
    private int counter = 0;

    void perform() {
        log.info("Thread - " + Thread.currentThread().getName() + " attempting to acquire the lock");
        lock.lock();
        log.info("Thread - " + Thread.currentThread().getName() + " acquired the lock");
        try {
            log.info("Thread - " + Thread.currentThread().getName() + " processing");
            counter++;
            Thread.sleep(100);
        } catch (Exception exception) {
            log.error(" Interrupted Exception ", exception);
        } finally {
            lock.unlock();
            log.info("Thread - " + Thread.currentThread().getName() + " released the lock");
        }
    }

    void performTryLock() {

        log.info("Thread - " + Thread.currentThread().getName() + " attempting to acquire the lock");
        try {
            boolean isLockAcquired = lock.tryLock(2, TimeUnit.SECONDS);
            if (isLockAcquired) {
                try {
                    log.info("Thread - " + Thread.currentThread().getName() + " acquired the lock");

                    log.info("Thread - " + Thread.currentThread().getName() + " processing");
                    Thread.sleep(1000);
                } finally {
                    lock.unlock();
                    log.info("Thread - " + Thread.currentThread().getName() + " released the lock");

                }
            } else {
                log.info("Thread - " + Thread.currentThread().getName() + " could not acquire the lock");
            }
        } catch (InterruptedException exception) {
            log.error(" Interrupted Exception ", exception);
        }
    }

    public ReentrantLock getLock() {
        return lock;
    }

    boolean isLocked() {
        return lock.isLocked();
    }

    boolean hasQueuedThreads() {
        return lock.hasQueuedThreads();
    }

    int getCounter() {
        return counter;
    }

    public static void main(String[] args) {

        final int threadCount = 2;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final SharedObjectWithLock object = new SharedObjectWithLock();

        service.execute(object::perform);
        service.execute(object::performTryLock);

        service.shutdown();

    }
}
