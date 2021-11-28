package io.github.kavahub.learnjava.sequence;

/**
 * 
 * 序列生成器，使用 {@code synchronized block} 实现
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SequenceGeneratorUsingSynchronizedBlock extends SequenceGenerator {
    private Object mutex = new Object();

    @Override
    public int getNextSequence() {
        synchronized (mutex) {
            return super.getNextSequence();
        }
    }

}
