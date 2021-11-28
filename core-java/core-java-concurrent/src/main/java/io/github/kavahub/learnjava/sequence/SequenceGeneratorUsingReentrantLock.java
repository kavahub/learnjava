package io.github.kavahub.learnjava.sequence;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 序列生成器，使用 {@link ReentrantLock} 实现
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SequenceGeneratorUsingReentrantLock extends SequenceGenerator {

    private ReentrantLock mutex = new ReentrantLock();

    @Override
    public int getNextSequence() {
        try {
            mutex.lock();
            return super.getNextSequence();
        } finally {
            mutex.unlock();
        }
    }
    
}
