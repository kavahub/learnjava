package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 6 解决公平性
 */
public class FairnessBoundedBlockingQueueV5<T> implements Queue<T> {
    // 当前大小
    protected AtomicInteger size;

    // 容量
    protected final int capacity;

    // 头指针，empty: head.next == tail == null
    protected Node<T> head;

    // 尾指针
    protected Node<T> tail;

    // guard: canPollCount, head, pollQueue
    protected final Object pollLock = new Object();
    protected int canPollCount;

    // guard: canOfferCount, tail, offerQueue
    protected final Object offerLock = new Object();
    protected int canOfferCount;

    protected final WaitQueue pollQueue = new WaitQueue();
    protected final WaitQueue offerQueue = new WaitQueue();

    public FairnessBoundedBlockingQueueV5(int capacity) {
        this.capacity = capacity;
        this.canOfferCount = capacity;
        this.canPollCount = 0;
        this.head = new Node<T>();
        this.tail = head;
        this.size = new AtomicInteger(0);
    }

    // 如果队列已满，通过返回值标识
    public boolean offer(T value) {
        if (Thread.interrupted()) {
            throw new RuntimeException("线程已中断"); // 线程已中断，直接退出即可，防止中断线程竞争锁
        }
        WaitNode wait = null;
        synchronized(offerLock) {
            // 在有阻塞请求或者队列为空时，阻塞等待
            if (canOfferCount <= 0 || !offerQueue.isEmpty()) {
                wait = new WaitNode();
                offerQueue.enq(wait);
            } else {
                // continue.
            }
        }

        try {
            if (wait != null) {
                wait.doWait();
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        } catch (InterruptedException e) {
            offerQueue.doNotify();
            throw new RuntimeException("线程已中断"); 
        }

        // 确保此时线程状态正常，以下不会校验中断
        synchronized(offerLock) {
            Node<T> node = new Node<T>(value);
            tail.next = node;
            tail = node;
            canOfferCount--;
        }
        synchronized(pollLock) {
            ++canPollCount;
            pollQueue.doNotify();
        }
        return true;
    }

    // 如果队列为空，阻塞等待
    public T poll() {
        if (Thread.interrupted()) {
            throw new RuntimeException("线程已中断"); 
        }
        T result = null;
        WaitNode wait = null;
        synchronized(pollLock) {
            // 在有阻塞请求或者队列为空时，阻塞等待
            if (canPollCount <= 0 || !pollQueue.isEmpty()) {
                wait = new WaitNode();
                pollQueue.enq(wait);
            } else {
                // ignore
            }
        }

        try {
            if (wait != null) {
                wait.doWait();
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        } catch (InterruptedException e) {
            // 传递消息
            pollQueue.doNotify();
            throw new RuntimeException("线程已中断"); 
        }

        // 以下不会检测线程中断状态
        synchronized(pollLock) {
            result = head.next.value;
            head.next.value = null;
            // ignore head;
            head = head.next;
            canPollCount--;
        }

        synchronized(offerLock) {
            canOfferCount++;
            offerQueue.doNotify();
        }
        return result;
    }

    @Override
    public int size() {
        return size.get();
    }

    class WaitQueue {

        WaitNode head;
        WaitNode tail;

        WaitQueue() {
            head = new WaitNode();
            tail = head;
        }

        synchronized void doNotify() {
            for (;;) {
                WaitNode node = deq();
                if (node == null) {
                    break;
                } else if (node.doNotify()) {
                    // 此处确保NOTIFY成功
                    break;
                } else {
                    // ignore, and retry.
                }
            }
        }

        synchronized boolean isEmpty() {
            return head.next == null;
        }

        synchronized void enq(WaitNode node) {
            tail.next = node;
            tail = tail.next;
        }

        synchronized WaitNode deq() {
            if (head.next == null) {
                return null;
            }
            WaitNode res = head.next;
            head = head.next;
            if (head.next == null) {
                tail = head; // 为空，迁移tail节点
            }
            return res;
        }
    }

    class WaitNode {
        boolean released;
        WaitNode next;

        WaitNode() {
            released = false;
            next = null;
        }

        synchronized void doWait() throws InterruptedException {
            try {
                while (!released) {
                    wait();
                }
            } catch (InterruptedException e) {
                if (!released) {
                    released = true;
                    throw e;
                } else {
                    // 如果是NOTIFY之后收到中断的信号，不能抛出异常；需要做RELAY处理
                    Thread.currentThread().interrupt();
                }
            }
        }

        synchronized void doWait(long milliSeconds) throws InterruptedException, TimeoutException {
            try {
                long startTime = System.nanoTime();
                long toWait = milliSeconds;
                for (;;) {
                    wait(toWait);
                    if (released) {
                        return;
                    }
                    long now = System.nanoTime();
                    toWait = toWait - (now - startTime);
                    if (toWait <= 0) {
                        throw new TimeoutException();
                    }
                }
            } catch (InterruptedException e) {
                if (!released) {
                    released = true;
                    throw e;
                } else {
                    // 如果已经释放信号量，此处不抛出异常；但恢复中断状态
                    Thread.currentThread().interrupt();
                }
            }
        }

        synchronized boolean doNotify() {
            if (!released) {
                released = true;
                notify();
                // 明确释放了一个线程，返回true
                return true;
            } else {
                // 没有释放新的线程，返回false
                return false;
            }
        }
    }
}
