package io.github.kavahub.learnjava.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link CountdownLatch} 不能被重置，只能用一次
 */
@Slf4j
public class CountdownLatchResetExample {
    private int count;
    private int threadCount;
    private final AtomicInteger updateCount;

    CountdownLatchResetExample(int count, int threadCount) {
        updateCount = new AtomicInteger(0);
        this.count = count;
        this.threadCount = threadCount;
    }

    public int countWaits() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(count);
        ExecutorService es = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            es.execute(() -> {
                long prevValue = countDownLatch.getCount();
                countDownLatch.countDown();
                // getCount()的值不会小于0
                if (countDownLatch.getCount() != prevValue) {
                    updateCount.incrementAndGet();
                }  
                
                log.info("i = {} is end", index);           
            });
        }
        
        countDownLatch.await();
        log.info("After Count {} ", countDownLatch.getCount());  

        es.shutdown();
        return updateCount.get();
    }

    public static void main(String[] args) throws InterruptedException {
        CountdownLatchResetExample ex = new CountdownLatchResetExample(7, 20);
        // ex.countWaits()返回值，有时候是8， 大部分是7
        log.info("Count : {}", ex.countWaits());
    }
}
