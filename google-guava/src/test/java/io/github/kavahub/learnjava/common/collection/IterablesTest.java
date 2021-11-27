package io.github.kavahub.learnjava.common.collection;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.junit.jupiter.api.Test;

/**
 * {@link Iterables} 示例
 *  
 * @author PinWei Wan
 * @since 1.0.0
 */
public class IterablesTest {
    @Test
    public final void givenUnmodifiableViewOverIterable_whenTryingToRemove_thenNotAllowed() {
        final List<Integer> numbers = Lists.newArrayList(1, 2, 3);
        final Iterable<Integer> unmodifiableIterable = Iterables.unmodifiableIterable(numbers);
        final Iterator<Integer> iterator = unmodifiableIterable.iterator();
        
        assertThrows(UnsupportedOperationException.class, () -> iterator.remove());
    }
}
