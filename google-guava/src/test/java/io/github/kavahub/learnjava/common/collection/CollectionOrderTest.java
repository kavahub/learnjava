package io.github.kavahub.learnjava.common.collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Comparators;

import org.junit.jupiter.api.Test;

/**
 * 集合排序
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 * @see Comparators#isInOrder(Iterable, Comparator)
 */
public class CollectionOrderTest {
    @Test
    public void isInOrderTest() {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 4, 6, 7, 8, 9, 10);

        boolean isInAscendingOrder = Comparators.isInOrder(numbers, new AscendingOrderComparator<Number>());

        assertTrue(isInAscendingOrder);
    }

    @Test
    public void isInStrictOrderTest() {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 3, 6, 7, 8, 9, 10);

        boolean isInAscendingOrder = Comparators.isInOrder(numbers, new AscendingOrderComparator<Number>());

        assertFalse(isInAscendingOrder);
    }

    private class AscendingOrderComparator<I extends Number> implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }
}
