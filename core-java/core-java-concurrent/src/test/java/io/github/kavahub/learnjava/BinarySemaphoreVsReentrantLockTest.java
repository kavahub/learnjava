package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Semaphore} 与 {@link ReentrantLock} 比较
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class BinarySemaphoreVsReentrantLockTest {
    @Test
    public void givenBinarySemaphore_whenAcquireAndRelease_thenCheckAvailablePermits() throws InterruptedException {
        Semaphore binarySemaphore = new Semaphore(1); 
        try { 
            binarySemaphore.acquire(); 
            assertEquals(0, binarySemaphore.availablePermits());
        } catch (InterruptedException e) { 
            e.printStackTrace(); 
        } finally { 
            binarySemaphore.release(); 
            assertEquals(1, binarySemaphore.availablePermits()); 
        }
    }

    @Test
    public void givenReentrantLock_whenLockAndUnlock_thenCheckHoldCountAndIsLocked() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock(); 
        try { 
            reentrantLock.lock(); 
            assertEquals(1, reentrantLock.getHoldCount()); 
            assertEquals(true, reentrantLock.isLocked()); 
        } finally { 
            reentrantLock.unlock(); 
            assertEquals(0, reentrantLock.getHoldCount()); 
            assertEquals(false, reentrantLock.isLocked());
        }
    }
    
    @Test
    public void givenReentrantLock_whenLockMultipleTimes_thenUnlockMultipleTimesToRelease() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock(); 
        try {
            // 可重入锁的特点 
            reentrantLock.lock(); 
            reentrantLock.lock();
            assertEquals(2, reentrantLock.getHoldCount()); 
            assertEquals(true, reentrantLock.isLocked()); 
        } finally { 
            reentrantLock.unlock(); 
            assertEquals(1, reentrantLock.getHoldCount()); 
            assertEquals(true, reentrantLock.isLocked());
            
            reentrantLock.unlock(); 
            assertEquals(0, reentrantLock.getHoldCount()); 
            assertEquals(false, reentrantLock.isLocked());
        }
    }
    
}
