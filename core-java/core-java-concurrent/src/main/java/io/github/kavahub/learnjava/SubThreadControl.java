package io.github.kavahub.learnjava;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 
 * 子线程控制，自己创建 {@code Thread} 运行自己
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
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

    @Override
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
