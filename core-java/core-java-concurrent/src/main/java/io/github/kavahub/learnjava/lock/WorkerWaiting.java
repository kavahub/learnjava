package io.github.kavahub.learnjava.lock;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * 等待的任务
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class WorkerWaiting implements Runnable {

    private final List<String> outputScraper;
    private final CountDownLatch readyThreadCounter;
    private final CountDownLatch callingThreadBlocker;
    private final CountDownLatch completedThreadCounter;

    public WorkerWaiting(final List<String> outputScraper, final CountDownLatch readyThreadCounter, final CountDownLatch callingThreadBlocker, CountDownLatch completedThreadCounter) {

        this.outputScraper = outputScraper;
        this.readyThreadCounter = readyThreadCounter;
        this.callingThreadBlocker = callingThreadBlocker;
        this.completedThreadCounter = completedThreadCounter;
    }

    @Override
    public void run() {
        // Mark this thread as read / started
        readyThreadCounter.countDown();
        try {
            callingThreadBlocker.await();
            outputScraper.add("Counted down");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            completedThreadCounter.countDown();
        }
    }
    
}
