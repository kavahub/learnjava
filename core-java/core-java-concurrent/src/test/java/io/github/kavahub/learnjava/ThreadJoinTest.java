package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadJoinTest {
    class SampleThread extends Thread {
        // 如果不定义 volatile， 测试givenThreadTerminated_checkForEffect_notGuaranteed无法停止
        public volatile int processingCount = 0;

        SampleThread(int processingCount) {
            this.processingCount = processingCount;
            log.info("Thread " + this.getName() + " created");
        }

        @Override
        public void run() {
            log.info("Thread " + this.getName() + " started");
            while (processingCount > 0) {
                try {
                    Thread.sleep(1000); // Simulate some work being done by thread
                } catch (InterruptedException e) {
                    log.info("Thread " + this.getName() + " interrupted.");
                }
                processingCount--;
                log.info("Inside Thread " + this.getName() + ", processingCount = " + processingCount);
            }
            log.info("Thread " + this.getName() + " exiting");
        }
    }

    @Test
    public void givenNewThread_whenJoinCalled_returnsImmediately() throws InterruptedException {
        Thread t1 = new SampleThread(0);
        log.info("Invoking join.");
        t1.join();
        log.info("Returned from join");
        log.info("Thread state is" + t1.getState());
        assertFalse(t1.isAlive());

        // 线程t1创建了，但未运行
    }

    @Test
    public void givenStartedThread_whenJoinCalled_waitsTillCompletion() throws InterruptedException {
        Thread t2 = new SampleThread(2);
        t2.start();
        log.info("Invoking join.");
        t2.join();
        log.info("Returned from join");
        assertFalse(t2.isAlive());
    }

    @Test
    public void givenStartedThread_whenTimedJoinCalled_waitsUntilTimedout() throws InterruptedException {
        Thread t3 = new SampleThread(5);
        t3.start();
        t3.join(1000);
        assertTrue(t3.isAlive());

        // 线程未运行完成
    }

    @Test
    public void givenThreadTerminated_checkForEffect_notGuaranteed() throws InterruptedException {
        SampleThread t4 = new SampleThread(5);
        t4.start();
        log.info("t4.processingCount: {}", t4.processingCount);
        // not guaranteed to stop even if t4 finishes.
        do {
            
        } while (t4.processingCount > 0);
        log.info("t4.processingCount: {}", t4.processingCount);
    }

    @Test
    public void givenThreadTerminated_checkForEffect_End() throws InterruptedException {
        SampleThread t4 = new SampleThread(5);
        t4.start();
        log.info("t4.processingCount: {}", t4.processingCount);
        do {
        } while (t4.isAlive());
        log.info("t4.processingCount: {}", t4.processingCount);
    }

    @Test
    public void givenJoinWithTerminatedThread_checkForEffect_guaranteed() throws InterruptedException {
        SampleThread t4 = new SampleThread(5);
        t4.start();
        do {
            t4.join(100);
        } while (t4.processingCount > 0);
    }

    @Test
    public void givenJoinWithTerminatedThread_checkForEffect_alive() throws InterruptedException {
        SampleThread t4 = new SampleThread(5);
        t4.start();
        do {
            t4.join(100);
        } while (t4.isAlive());
    }
}
