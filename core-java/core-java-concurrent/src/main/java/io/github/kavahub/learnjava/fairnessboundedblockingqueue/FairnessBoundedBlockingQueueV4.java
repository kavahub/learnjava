package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 4 状态追踪解除竞争
 */
public class FairnessBoundedBlockingQueueV4<T> implements Queue<T> {
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
    protected int waitPollCount;

    // guard: canOfferCount, tail
    protected final Object offerLock = new Object();
    protected int canOfferCount;
    protected int waitOfferCount;

    public FairnessBoundedBlockingQueueV4(int capacity) {
        this.capacity = capacity;
        this.canPollCount = 0;
        this.canOfferCount = capacity;
        this.waitPollCount = 0;
        this.waitOfferCount = 0;
        this.head = new Node<T>();
        this.tail = head;
        this.size = new AtomicInteger(0);
    }

    // 如果队列已满，通过返回值标识
    public boolean offer(T value) {
        try {
            synchronized (offerLock) {
                while (canOfferCount <= 0) {
                    waitOfferCount++;
                    offerLock.wait();
                    waitOfferCount--;
                }

                // try {
                //     TimeUnit.MILLISECONDS.sleep(10);
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
                
                Node<T> node = new Node<T>(value);
                tail.next = node;
                tail = node;
                canOfferCount--;
                size.incrementAndGet();
            }
            synchronized (pollLock) {
                ++canPollCount;
                if (waitPollCount > 0) {
                    pollLock.notify();
                }
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
            T result;
            synchronized (pollLock) {
                while (canPollCount <= 0) {
                    waitPollCount++;
                    pollLock.wait();
                    waitPollCount--;
                }

                // try {
                //     TimeUnit.MILLISECONDS.sleep(10);
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }

                result = head.next.value;
                head.next.value = null;
                head = head.next;
                canPollCount--;
                size.decrementAndGet();
                
            }
            synchronized (offerLock) {
                canOfferCount++;
                if (waitOfferCount > 0) {
                    offerLock.notify();
                }
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
