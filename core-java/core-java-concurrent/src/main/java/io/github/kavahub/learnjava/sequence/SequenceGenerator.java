package io.github.kavahub.learnjava.sequence;

/**
 * 
 * 序列生成器，非线程安全
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SequenceGenerator {
    private int currentValue = 0;

    public int getNextSequence() {
        return currentValue++;
    }
}
