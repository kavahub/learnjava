package io.github.kavahub.learnjava.sequence;

import com.google.common.util.concurrent.Monitor;

/**
 * 
 * 序列生成器，使用 {@link Monitor} 实现
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SequenceGeneratorUsingMonitor extends SequenceGenerator {

    private Monitor mutex = new Monitor();

    @Override
    public int getNextSequence() {
        mutex.enter();
        try {
            return super.getNextSequence();
        } finally {
            mutex.leave();
        }
    }
    
}
