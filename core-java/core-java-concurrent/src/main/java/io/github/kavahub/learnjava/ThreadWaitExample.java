package io.github.kavahub.learnjava;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 线程等待
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class ThreadWaitExample {
    private static final Object LOCK = new Object();

    public static void main(String... args) throws InterruptedException {
        sleepWaitInSynchronizedBlocks();
    }

    private static void sleepWaitInSynchronizedBlocks() throws InterruptedException {
        Thread.sleep(1000); // called on the thread
        log.debug("Thread '" + Thread.currentThread().getName() + "' is woken after sleeping for 1 second");

        synchronized (LOCK) {
            LOCK.wait(1000); // called on the object, synchronization required
            log.debug("Object '" + LOCK + "' is woken after waiting for 1 second");
        }
    } 
}
