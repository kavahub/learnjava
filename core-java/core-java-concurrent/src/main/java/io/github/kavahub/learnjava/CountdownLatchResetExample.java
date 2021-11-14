package io.github.kavahub.learnjava;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

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
                if (countDownLatch.getCount() != prevValue) {
                    updateCount.incrementAndGet();
                }  
                log.info("i = {} is end", index);           
            });
        }
        
        countDownLatch.await();
        es.shutdown();
        return updateCount.get();
    }

    public static void main(String[] args) throws InterruptedException {
        CountdownLatchResetExample ex = new CountdownLatchResetExample(7, 20);
        System.out.println("Count : " + ex.countWaits());
    }
}
