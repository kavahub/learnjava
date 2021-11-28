package io.github.kavahub.learnjava.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 栅栏类似于闭锁，它能阻塞一组线程直到某个事件的发生。栅栏与闭锁的关键区别在于，
 * 所有的线程必须同时到达栅栏位置，才能继续执行。闭锁用于等待事件，而栅栏用于等待其他线程。
 * 
 * <p>
 * {@link CyclicBarrier} 可以使一定数量的线程反复地在栅栏位置处汇集。当线程到达栅栏位置时将调用await方法，
 * 这个方法将阻塞直到所有线程都到达栅栏位置。如果所有线程都到达栅栏位置，那么栅栏将打开， 此时所有的线程都将被释放，而栅栏将被重置以便下次使用。
 * 
 * <p>
 * {@link CyclicBarrier#wait()}一直处于等待状态，除非发生以下情况：
 * <ul>
 * <li>最后一个线程到达，即index == 0</li>
 * <li>某个参与线程等待超时</li>
 * <li>某个参与线程被中断</li>
 * <li>调用了CyclicBarrier的reset()方法。该方法会将屏障重置为初始状态</li>
 * </ul>
 * 
 * <p>
 * {@link CyclicBarrier} 和 {@link CountDownLatch} 的区别:
 * <ul>
 * <li>CountDownLatch的计数器只能使用一次，而CyclicBarrier的计数器可以使用reset()方法重置，可以使用多次，
 * 所以CyclicBarrier能够处理更为复杂的场景；</li>
 * <li>
 * CyclicBarrier还提供了一些其他有用的方法，比如getNumberWaiting()方法可以获得CyclicBarrier阻塞的线程数量，
 * isBroken()方法用来了解阻塞的线程是否被中断；</li>
 * <li>CountDownLatch允许一个或多个线程等待一组事件的产生，而CyclicBarrier用于等待其他线程运行到栅栏位置。</li>
 * </ul>
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CyclicBarrierCompletionMethodExample {
    private int count;
    private int threadCount;
    private final AtomicInteger updateCount;

    CyclicBarrierCompletionMethodExample(int count, int threadCount) {
        updateCount = new AtomicInteger(0);
        this.count = count;
        this.threadCount = threadCount;
    }

    public int countTrips() {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(count, () -> {
            updateCount.incrementAndGet();
        });

        ExecutorService es = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            es.execute(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        es.shutdown();
        try {
            es.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return updateCount.get();
    }

    public static void main(String[] args) {
        // 可以调整threadCount的值，如果 threadCount % count = 0 时，主线程会结束；如果不等0，主线程一直等待
        CyclicBarrierCompletionMethodExample ex = new CyclicBarrierCompletionMethodExample(5, 21);
        System.out.println("Count : " + ex.countTrips());
    }
}
