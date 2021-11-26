package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 3 锁拆分优化
 */
public class FairnessBoundedBlockingQueueV3<T> implements Queue<T> {
    // 当前大小
    protected AtomicInteger size;

    // 容量
    protected final int capacity;

    // 头指针，empty: head.next == tail == null
    protected Node<T> head;

    // 尾指针
    protected Node<T> tail;

    // guard: canPollCount, head
    protected final Object pollLock = new Object();
    protected int canPollCount;

    // guard: canOfferCount, tail
    protected final Object offerLock = new Object();
    protected int canOfferCount;

    public FairnessBoundedBlockingQueueV3(int capacity) {
        this.capacity = capacity;
        this.canPollCount = 0;
        this.canOfferCount = capacity;
        this.head = new Node<T>();
        this.tail = head;
        this.size = new AtomicInteger(0);
    }

    // 如果队列已满，通过返回值标识
    public boolean offer(T value) {
        try {
            synchronized (offerLock) {
                while (canOfferCount <= 0) {
                    offerLock.wait();
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
                canOfferCount--;
            }
            synchronized (pollLock) {
                ++canPollCount;
                pollLock.notifyAll();
            }

            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        return false;
    }

    // 如果队列为空，阻塞等待
    public T poll() {
        try {
            T result = null;
            synchronized (pollLock) {
                while (canPollCount <= 0) {
                    pollLock.wait();
                }

                // try {
                //     TimeUnit.MILLISECONDS.sleep(10);
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
                
                result = head.next.value;
                head.next.value = null;
                head = head.next;
                size.decrementAndGet();
                canPollCount--;
            }
            synchronized (offerLock) {
                canOfferCount++;
                offerLock.notifyAll();
            }
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
