package io.github.kavahub.learnjava.counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * {@link ReentrantReadWriteLock} 锁计数器
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ReentrantReadWriteLockCounter {
    private int counter;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();
    
    public ReentrantReadWriteLockCounter() {
        this.counter = 0;
    }
    
    public void incrementCounter() {
        writeLock.lock();
        try {
            counter += 1;
        } finally {
            writeLock.unlock();
        }
    }
    
    public int getCounter() {
        readLock.lock();
        try {
            return counter;
        } finally {
            readLock.unlock();
        }
    }
}
