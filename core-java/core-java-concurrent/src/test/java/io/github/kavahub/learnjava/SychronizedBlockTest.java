package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SychronizedBlockTest {
    @Test
    public void givenMultiThread_whenBlockSync() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        SynchronizedBlocks synchronizedBlocks = new SynchronizedBlocks();

        // 没有参数的方法也是一个Runable
        IntStream.range(0, 1000)
          .forEach(count -> service.submit(synchronizedBlocks::performSynchronisedTask));
        service.awaitTermination(500, TimeUnit.MILLISECONDS);

        assertEquals(1000, synchronizedBlocks.getCount());

        service.shutdown();
    }

    @Test
    public void givenMultiThread_whenStaticSyncBlock() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        IntStream.range(0, 1000)
          .forEach(count -> service.submit(SynchronizedBlocks::performStaticSyncTask));
        service.awaitTermination(500, TimeUnit.MILLISECONDS);

        assertEquals(1000, SynchronizedBlocks.getStaticCount());

        service.shutdown();
    }

    @Test
    public void givenHoldingTheLock_whenReentrant_thenCanAcquireItAgain() {
        Object lock = new Object();
        synchronized (lock) {
            System.out.println("First time acquiring it");

            synchronized (lock) {
                System.out.println("Entering again");

                synchronized (lock) {
                    System.out.println("And again");
                }
            }
        }
    }

    public static class SynchronizedBlocks {

        private int count = 0;
        private static int staticCount = 0;
    
        void performSynchronisedTask() {
            // 可以发现，不同的线程取到相同的值；有些值没有取到
            log.info("{} count = {}", Thread.currentThread().getName(), getCount());
            synchronized (this) {
                setCount(getCount() + 1);
            }
        }
    
        static void performStaticSyncTask() {
            synchronized (SynchronizedBlocks.class) {
                setStaticCount(getStaticCount() + 1);
            }
        }
    
        public int getCount() {
            return count;
        }
    
        public void setCount(int count) {
            this.count = count;
        }
    
        static int getStaticCount() {
            return staticCount;
        }
    
        private static void setStaticCount(int staticCount) {
            SynchronizedBlocks.staticCount = staticCount;
        }
    }
}
