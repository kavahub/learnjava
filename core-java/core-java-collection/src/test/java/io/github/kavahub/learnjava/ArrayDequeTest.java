package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Test;

/**
 * ArrayDeque是Deque接口的一个实现，使用了可变数组，所以没有容量上的限制。
 * 同时，ArrayDeque是线程不安全的，在没有外部同步的情况下，不能再多线程环境下使用。
 * ArrayDeque是Deque的实现类，可以作为栈来使用，效率高于Stack； 也可以作为队列来使用，效率高于LinkedList。
 * 需要注意的是，ArrayDeque不支持null值。
 */
public class ArrayDequeTest {
    @Test
    public void whenOffer_addsAtLast() {
        final Deque<String> deque = new ArrayDeque<>();

        deque.offer("first");
        deque.offer("second");

        assertEquals("second", deque.getLast());
    }

    @Test
    public void whenPoll_removesFirst() {
        final Deque<String> deque = new ArrayDeque<>();

        deque.offer("first");
        deque.offer("second");

        assertEquals("first", deque.poll());
    }

    @Test
    public void whenPush_addsAtFirst() {
        final Deque<String> deque = new ArrayDeque<>();

        deque.push("first");
        deque.push("second");

        assertEquals("second", deque.getFirst());
    }

    @Test
    public void whenPop_removesLast() {
        final Deque<String> deque = new ArrayDeque<>();

        deque.push("first");
        deque.push("second");

        assertEquals("second", deque.pop());
    }
}
