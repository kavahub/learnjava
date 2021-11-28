package io.github.kavahub.learnjava.counter;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 
 * {@link Semaphore} 计数器
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SemaphoreCounter {
    private final Semaphore lock;
    private int count;

    public SemaphoreCounter() {
        lock = new Semaphore(1);
        count = 0;
    }

    public void incrementCounter() throws InterruptedException {
        try {
            lock.acquire();
            this.count = this.count + 1;
            TimeUnit.SECONDS.sleep(1);
            System.out.println(System.currentTimeMillis());
        } finally {
            lock.release();
        }
    }

    public int getCounter() {
        return this.count;
    }

    public boolean hasQueuedThreads() {
        return lock.hasQueuedThreads();
    }
}
