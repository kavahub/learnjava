package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

import java.util.concurrent.TimeUnit;

/**
 * 1 基础版本
 */
public class FairnessBoundedBlockingQueueV1<T> implements Queue<T> {
    // 当前大小
    protected int size;

    // 容量
    protected final int capacity;

    // 头指针，empty: head.next == tail == null
    protected Node<T> head;

    // 尾指针
    protected Node<T> tail;

    public FairnessBoundedBlockingQueueV1(int capacity) {
        this.capacity = capacity;
        this.head = new Node<T>();
        this.tail = head;
        this.size = 0;
    }

    // 如果队列已满，通过返回值标识
    public boolean offer(T obj) {
        if (size < capacity) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            Node<T> node = new Node<T>(obj);
            tail.next = node;
            tail = node;
            ++size;
            return true;
        }
        return false;
    }

    // 如果队列为空，head.next == null；返回空元素
    public T poll() {
        if (head.next != null) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            T result = head.next.value;
            head.next.value = null;
            head = head.next; // 丢弃头结点
            --size;
            return result;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }
}
