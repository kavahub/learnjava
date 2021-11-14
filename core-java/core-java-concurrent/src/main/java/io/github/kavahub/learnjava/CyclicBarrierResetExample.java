package io.github.kavahub.learnjava;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

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

        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);

        ExecutorService es = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            es.execute(() -> {
                try {
                    //log.info("i = {} is begin", index);
 
                    // getNumberWaiting该方法的作用是获得有几个线程已经到达屏障点。
                    final int numberWaiting = cyclicBarrier.getNumberWaiting();
                    if (numberWaiting > 0) {
                        //log.info("getNumberWaiting = {}", numberWaiting);
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

    /**
     * 结论：
     * CyclicBarrier的大小与线程数成倍数关系，所有的线程才能结束，否则会等待。
     * @param args
     */
    public static void main(String[] args) {
        // 调整cout参数观察情况, 说明如下：
        // > 当count是threadCount的倍数时, 如：2， 4， 5...，所有线程会结束（end），程序自动退出；否则，有些线程一直等待，程序无法停止。
        // > 结束的线程（end）是count参数的倍数，但小于threadCount
        CyclicBarrierResetExample ex = new CyclicBarrierResetExample(7, 20);
        System.out.println("Count : " + ex.countWaits());
    }
}
