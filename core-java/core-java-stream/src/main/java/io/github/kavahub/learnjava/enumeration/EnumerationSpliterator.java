package io.github.kavahub.learnjava.enumeration;

import java.util.Enumeration;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;

/**
 * 
 * {@link Enumeration} 分段器
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class EnumerationSpliterator<T> extends AbstractSpliterator<T> {
    private final Enumeration<T> enumeration;

    public EnumerationSpliterator(long est, int additionalCharacteristics, Enumeration<T> enumeration) {
        super(est, additionalCharacteristics);
        this.enumeration = enumeration;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (enumeration.hasMoreElements()) {
            action.accept(enumeration.nextElement());
            return true;
        }
        return false;
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        while (enumeration.hasMoreElements())
            action.accept(enumeration.nextElement());
    }   
}
