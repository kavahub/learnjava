package io.github.kavahub.learnjava;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 线程唤醒
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class ThreadWakingUpExample {
    private static final ThreadAnthor b = new ThreadAnthor();

    public static void main(String... args) throws InterruptedException {
        b.start();

        synchronized (b) {
            while (b.sum == 0) {
                log.debug("Waiting for ThreadAnthor to complete...");
                b.wait();
            }

            log.debug("ThreadAnthor has completed. Sum from that thread is: " + b.sum);
        }
    }

    static class ThreadAnthor extends Thread {
        int sum;
    
        @Override
        public void run() {
            synchronized (this) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                int i = 0;
                while (i < 100000) {
                    sum += i;
                    i++;
                }
                // 非必须的，可能线程可以自动notify
                // notify();
            }
        }
    }
}
