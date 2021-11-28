package io.github.kavahub.learnjava.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * {@link CyclicBarrier} 复位示例 
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class CyclicBarrierResetExample {
    private int count;
    private int threadCount;
    private final AtomicInteger updateCount;

    CyclicBarrierResetExample(int count, int threadCount) {
        updateCount = new AtomicInteger(0);
        this.count = count;
        this.threadCount = threadCount;
    }

    public int countWaits() {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(count, () -> {
            log.info("等待进程数量已经达到count值");
        });

        ExecutorService es = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            es.execute(() -> {
                try {
                    // getNumberWaiting该方法的作用是获得有几个线程已经到达屏障点。
                    final int numberWaiting = cyclicBarrier.getNumberWaiting();
                    if (numberWaiting > 0) {
                        updateCount.incrementAndGet();
                    }  
                    cyclicBarrier.await();


                    log.info("i = {} is end", index);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        
        
        try {
            TimeUnit.SECONDS.sleep(1);
            es.shutdown();

            es.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return updateCount.get();
    }

    public static void main(String[] args) {
        CyclicBarrierResetExample ex = new CyclicBarrierResetExample(7, 20);
        System.out.println("Count : " + ex.countWaits());
    }
}
