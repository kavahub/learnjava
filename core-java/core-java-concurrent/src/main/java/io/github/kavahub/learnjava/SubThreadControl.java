package io.github.kavahub.learnjava;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class SubThreadControl implements Runnable {

    private Thread worker;
    private AtomicBoolean running = new AtomicBoolean(false);

    public SubThreadControl() {
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
        running.set(true);
    }

    public void stop() {
        running.set(false);
    }

    public void interrupt() {
        running.set(false);
        worker.interrupt();
    }

    boolean isRunning() {
        return running.get();
    }

    boolean isStopped() {
        return !isRunning();
    }

    public void run() {
        if (!running.get()) {
            throw new IllegalStateException("not running");
        }

        // 模拟业务
        final long worktime = ThreadLocalRandom.current().nextInt(1000, 3000);
        final long now = System.currentTimeMillis();
        while (System.currentTimeMillis() <= now + worktime) {
            // 判断当前线程状态
            if (Thread.currentThread().isInterrupted()) {
                Thread.currentThread().interrupt();
            }
        }

        running.set(false);
    }

}
