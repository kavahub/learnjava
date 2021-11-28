package io.github.kavahub.learnjava.sequence;

import java.util.concurrent.Semaphore;

/**
 * 
 * 序列生成器，使用 {@link Semaphore} 实现
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SequenceGeneratorUsingSemaphore extends SequenceGenerator {

    private Semaphore mutex = new Semaphore(1);

    @Override
    public int getNextSequence() {
        try {
            mutex.acquire();
            return super.getNextSequence();
        } catch (InterruptedException e) {
            throw new RuntimeException("Exception in critical section.", e);
        } finally {
            mutex.release();
        }
    }
    
}
