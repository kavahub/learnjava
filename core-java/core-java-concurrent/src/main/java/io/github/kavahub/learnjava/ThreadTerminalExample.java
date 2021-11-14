package io.github.kavahub.learnjava;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadTerminalExample {
    public static void main(String[] args) {
        ThreadTerminalExample stopExecution = new ThreadTerminalExample();
        stopExecution.testScheduledExecutor();
        log.info("done");
    }

    public void testScheduledExecutor() {
        log.info("testScheduledExecutor");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        Future<?> future = executor.submit(new LongRunningTask());
        executor.schedule(new Runnable() {
            public void run() {
                future.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    class LongRunningTask implements Runnable {
        @Override
        public void run() {
            log.info("Long running task started");

            final long worktime = 9 * 1000;
            final long now = System.currentTimeMillis();

            try {
                while (System.currentTimeMillis() <= now + worktime) {
                    
                    // 判断当前线程状态，重新抛出中断错误
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }
                }
                
                log.info("Long running operation complete");
            } catch (Exception e) {
                log.info("Long running operation interrupted");
            }

        }
    }

}
