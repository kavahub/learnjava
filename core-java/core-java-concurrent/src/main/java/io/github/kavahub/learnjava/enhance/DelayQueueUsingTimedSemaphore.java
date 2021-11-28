package io.github.kavahub.learnjava.enhance;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.TimedSemaphore;

/**
 * 
 */
/**
 * 
 * {@link TimedSemaphore} 允许在既定的时间内维护一定数量的
 * {@code Semaphore}（这段时间内和JDK实现的 {@code Semaphore} 效果一样），当时间过去后会释放所有的 {@code permits}
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class DelayQueueUsingTimedSemaphore {
    private final TimedSemaphore semaphore;

    public DelayQueueUsingTimedSemaphore(long period, int slotLimit) {
        semaphore = new TimedSemaphore(period, TimeUnit.SECONDS, slotLimit);
    }

    public boolean tryAdd() {
        return semaphore.tryAcquire();
    }

    public int availableSlots() {
        return semaphore.getAvailablePermits();
    }
}
