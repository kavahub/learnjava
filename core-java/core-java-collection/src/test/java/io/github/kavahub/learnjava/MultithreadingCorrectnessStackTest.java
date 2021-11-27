package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.junit.jupiter.api.Test;

import static java.util.stream.IntStream.range;

/**
 * 
 * 多线程读取栈示例
 * 
 * <p>
 * {@link ConcurrentLinkedDeque} 非阻塞，无锁，无界 ，线程安全双端操作的队列
 * 
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MultithreadingCorrectnessStackTest {
    @Test
    public void givenSynchronizedDeque_whenExecutedParallel_thenWorkRight() {

        DequeBasedSynchronizedStack<Integer> deque = new DequeBasedSynchronizedStack<>();

        // Serial execution of push on ConcurrentLinkedQueue will always result in correct execution.
        range(1, 10000).forEach(value -> deque.push(value));

        int sum = 0;
        while(deque.peek() != null) {
            sum += deque.pop();
        }

        assertEquals(49995000, sum);

        // Parallel execution of push on ConcurrentLinkedQueue will always result in correct execution.
        range(1, 10000).parallel().forEach(value -> deque.push(value));

        sum = 0;
        while(deque.peek() != null) {
            sum += deque.pop();
        }

        assertEquals(49995000, sum);
    }

    @Test
    public void givenConcurrentLinkedQueue_whenExecutedParallel_thenWorkRight() {
        // 一种基于链表的无界的同时支持FIFO、LIFO的非阻塞并发双端队列
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();

        // Serial execution of push on ConcurrentLinkedQueue will always result in correct execution.
        range(1, 10000).forEach(value -> deque.push(value));

        int sum = 0;
        while(deque.peek() != null) {
            sum += deque.pop();
        }

        assertEquals(49995000, sum);

        // Parallel execution of push on ConcurrentLinkedQueue will always result in correct execution.
        range(1, 10000).parallel().forEach(value -> deque.push(value));

        sum = 0;
        while(deque.peek() != null) {
            sum += deque.pop();
        }

        assertEquals(49995000, sum);
    }

    @Test
    public void givenArrayDeque_whenExecutedParallel_thenShouldFail() {

        ArrayDeque<Integer> deque = new ArrayDeque<>();

        // Serial execution of push on ArrayDeque will always result in correct execution.
        range(1, 10000).forEach(value -> deque.push(value));

        int sum = 0;
        while(deque.peek() != null) {
            sum += deque.pop();
        }

        assertEquals(49995000, sum);

        // Parallel execution of push on ArrayDeque will not result in correct execution.
        range(1, 10000).parallel().forEach(value -> deque.push(value));

        sum = 0;
        while(deque.peek() != null) {
            sum += deque.pop();
        }

        // This shouldn't happen.
        if(sum == 49995000) {
            System.out.println("Something wrong in the environment, Please try some big value and check");
            // To safe-guard build without test failures.
            return;
        }

        assertNotEquals(49995000, sum);
    }

    public class DequeBasedSynchronizedStack<T> {

        // Internal Deque which gets decorated for synchronization.
        private ArrayDeque<T> dequeStore = new ArrayDeque<>();
    
        public DequeBasedSynchronizedStack(int initialCapacity) {
            this.dequeStore = new ArrayDeque<>(initialCapacity);
        }
    
        public DequeBasedSynchronizedStack() {
    
        }
    
        public synchronized T pop() {
            return this.dequeStore.pop();
        }
    
        public synchronized void push(T element) {
            this.dequeStore.push(element);
        }
    
        public synchronized T peek() {
            return this.dequeStore.peek();
        }
    
        public synchronized int size() {
            return this.dequeStore.size();
        }
    }
}
