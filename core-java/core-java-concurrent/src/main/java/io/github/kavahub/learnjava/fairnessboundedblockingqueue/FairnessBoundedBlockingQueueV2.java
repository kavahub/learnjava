package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 2 并发版本
 */
public class FairnessBoundedBlockingQueueV2<T> implements Queue<T> {
    // 当前大小
    protected AtomicInteger size;

    // 容量
    protected final int capacity;

    // 头指针，empty: head.next == tail == null
    protected Node<T> head;

    // 尾指针
    protected Node<T> tail;

    public FairnessBoundedBlockingQueueV2(int capacity) {
        this.capacity = capacity;
        this.head = new Node<T>();
        this.tail = head;
        this.size = new AtomicInteger(0);
    }

    // 如果队列已满，通过返回值标识
    public synchronized boolean offer(T value) {
        try {           
            while (size.get() >= capacity) {
                wait();
            }

            // try {
            //     TimeUnit.MILLISECONDS.sleep(10);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }

            Node<T> node = new Node<T>(value);
            tail.next = node;
            tail = node;
            size.incrementAndGet();
            notifyAll(); // 可以出队
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        return false;
    }

    // 如果队列为空，阻塞等待
    public synchronized T poll() {
        try {
            while (head.next == null) {
                wait();
            }

            // try {
            //     TimeUnit.MILLISECONDS.sleep(10);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            
            T result = head.next.value;
            head.next.value = null;
            head = head.next; // 丢弃头结点
            size.decrementAndGet();
            notifyAll(); // 可以入队
            return result;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int size() {
        return size.get();
    }
}
