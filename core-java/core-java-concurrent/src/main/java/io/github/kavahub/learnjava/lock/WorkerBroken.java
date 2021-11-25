package io.github.kavahub.learnjava.lock;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class WorkerBroken implements Runnable {
    private final List<String> outputScraper;
    private final CountDownLatch countDownLatch;
    private boolean TRUE = true;

    public WorkerBroken(final List<String> outputScraper, final CountDownLatch countDownLatch) {
        this.outputScraper = outputScraper;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        if (TRUE) {
            throw new RuntimeException("Oh dear");
        }
        countDownLatch.countDown();
        outputScraper.add("Counted down");
    }
}
