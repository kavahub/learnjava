package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.Test;

/**
 * CopyOnWriteArrayList是ArrayList的线程安全版本，从他的名字可以推测，CopyOnWriteArrayList是在有写操作的时候会copy一份数据，
 * 然后写完再设置成新的数据。CopyOnWriteArrayList适用于读多写少的并发场景
 */
public class CopyOnWriteArrayListTest {
    @Test
    public void givenCopyOnWriteList_whenIterateAndAddElementToUnderneathList_thenShouldNotChangeIterator() {
        // given
        final CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(new Integer[] { 1, 3, 5, 8 });

        // when
        Iterator<Integer> iterator = numbers.iterator();
        numbers.add(10);

        // then
        List<Integer> result = new LinkedList<>();
        iterator.forEachRemaining(result::add);
        assertThat(result).containsOnly(1, 3, 5, 8);

        // and
        Iterator<Integer> iterator2 = numbers.iterator();
        List<Integer> result2 = new LinkedList<>();
        iterator2.forEachRemaining(result2::add);

        // then
        assertThat(result2).containsOnly(1, 3, 5, 8, 10);

    }

    @Test
    public void givenCopyOnWriteList_whenIterateOverItAndTryToRemoveElement_thenShouldThrowException() {
        // given
        final CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(new Integer[] { 1, 3, 5, 8 });

        // when
        Iterator<Integer> iterator = numbers.iterator();

        assertThrows(UnsupportedOperationException.class, () -> iterator.remove());
    }
}
