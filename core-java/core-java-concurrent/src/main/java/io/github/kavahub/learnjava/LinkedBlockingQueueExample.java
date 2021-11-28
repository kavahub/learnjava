package io.github.kavahub.learnjava;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * {@link LinkedBlockingQueue} 是一个单向链表实现的阻塞队列
 * 
 * <p>
 * 该队列按FIFO（先进先出）排序元素，新元素插入到队列的尾部，并且队列获取操作会获得位于队列头部的元素。
 * 链接队列的吞吐量通常要高于基于数组的队列，但是在大多数并发应用程序中，其可预知的性能要低。
 * 此外，{@code LinkedBlockingQueue} 还是可选容量的(防止过度膨胀)，即可以指定队列的容量。如果不指定，
 * 默认容量大小等于 {@code Integer#MAX_VALUE}。
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class LinkedBlockingQueueExample {
    public static void main(String[] args) {
        int BOUND = 10;
        int N_PRODUCERS = 4;
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
        int poisonPill = Integer.MAX_VALUE;
        int poisonPillPerProducer = N_CONSUMERS / N_PRODUCERS;
        int mod = N_CONSUMERS % N_PRODUCERS;
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(BOUND);

        for (int i = 1; i < N_PRODUCERS; i++) {
            new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer)).start();
        }

        for (int j = 0; j < N_CONSUMERS; j++) {
            new Thread(new NumbersConsumer(queue, poisonPill)).start();
        }

        new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer + mod)).start();

    }

    public static class NumbersProducer implements Runnable {

        private final BlockingQueue<Integer> numbersQueue;
        private final int poisonPill;
        private final int poisonPillPerProducer;

        NumbersProducer(BlockingQueue<Integer> numbersQueue, int poisonPill, int poisonPillPerProducer) {
            this.numbersQueue = numbersQueue;
            this.poisonPill = poisonPill;
            this.poisonPillPerProducer = poisonPillPerProducer;
        }

        public void run() {
            try {
                generateNumbers();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void generateNumbers() throws InterruptedException {
            for (int i = 0; i < 100; i++) {
                numbersQueue.put(ThreadLocalRandom.current().nextInt(100));
            }
            for (int j = 0; j < poisonPillPerProducer; j++) {
                numbersQueue.put(poisonPill);
            }
        }
    }

    public static class NumbersConsumer implements Runnable {
        private final BlockingQueue<Integer> queue;
        private final int poisonPill;

        NumbersConsumer(BlockingQueue<Integer> queue, int poisonPill) {
            this.queue = queue;
            this.poisonPill = poisonPill;
        }

        public void run() {
            try {
                while (true) {
                    Integer number = queue.take();
                    if (number.equals(poisonPill)) {
                        System.out.println(Thread.currentThread().getName() + " Consumer stoped");
                        return;
                    }
                    String result = number.toString();
                    System.out.println(Thread.currentThread().getName() + " result: " + result);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
