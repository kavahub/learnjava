package io.github.kavahub.learnjava.sequence;

/**
 * 
 * 序列生成器，使用 {@code synchronized method} 实现
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SequenceGeneratorUsingSynchronizedMethod extends SequenceGenerator {
    @Override
    public synchronized int getNextSequence() {
        return super.getNextSequence();
    }
    
}
