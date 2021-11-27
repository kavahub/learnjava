package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.iterators.ReverseListIterator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 
 * 迭代器反向
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ReverseIteratorManualTest {
    private final static int SIZE = 100;
    private final static List<Integer> list = new ArrayList<>();

    @BeforeAll
    public static void beforeAll() {
        for (int i = 0; i < SIZE; i++) {
            list.add(i);
        }
    }

    @Test
    public void iterateUsingForLoop() {
        for (int i = list.size(); i-- > 0;) {
            System.out.println(list.get(i));
        }
    }

    @Test
    public void iterateUsingListIterator() {
        final ListIterator<Integer> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            System.out.println(listIterator.previous());
        }
    }

    @Test
    public void iterateUsingCollections() {
        Collections.reverse(list);
        for (final Integer item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void iterateUsingApacheReverseListIterator() {
        final ReverseListIterator<Integer> listIterator = new ReverseListIterator<>(list);
        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }
    }

    @Test
    public void iterateUsingGuava() {
        final List<Integer> reversedList = Lists.reverse(list);
        for (final Integer item : reversedList) {
            System.out.println(item);
        }
    }
}
