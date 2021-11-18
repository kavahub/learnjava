package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.iterators.ReverseListIterator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
        // ListIterator是一个更加强大的Iterator的子类型,它只能用于各种List类的访问,尽管Iterator只能向前移动,但是ListIterator可以双向移动,它还可以产生相对于迭代器在列表指向的当前位置的前一个和后一个元素的索引,并且可以使用set()方法替换它访问过的最后一个元素.
        // 你可以通过ListIterator()方法产生一个指向List开始处的ListIteraor,并且还可以通过调用ListIterator(n)方法创建一个一开始就指向索引列表n的元素处的ListIterator
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
