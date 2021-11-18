package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

/**
 * LinkedList 是基于链表实现的（通过名字也能区分开来），所以它的插入和删除操作比 ArrayList 更加高效。
 * 但也是由于其为基于链表的，所以随机访问的效率要比 ArrayList 差。
 */
public class LinkedListTest {
    @Test
    void givenLinkedList_whenItemIsAppended_thenItCanBeRetrieved() {
        LinkedList<String> list = new LinkedList<>();
        list.addLast("Daniel");
        list.addFirst("Marko");
        assertThat(list).hasSize(2);
        assertThat(list.getLast()).isEqualTo("Daniel");
    }

    @Test
    void givenLinkedList_whenItemIsRemoved_thenListSizeIsReduced() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("Daniel", "Marko", "David"));
        list.removeFirst();
        list.removeLast();
        assertThat(list).hasSize(1);
        assertThat(list).containsExactly("Marko");
    }

    @Test
    void givenLinkedList_whenItemInserted_thenItCanBeRetrievedAndDeleted() {
        LinkedList<String> list = new LinkedList<>();
        list.push("Daniel");
        list.push("Marko");
        assertThat(list.poll()).isEqualTo("Marko");
        assertThat(list).hasSize(1);
    }
}
