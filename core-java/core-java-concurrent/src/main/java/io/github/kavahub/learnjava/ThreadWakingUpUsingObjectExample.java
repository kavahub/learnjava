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
public class ThreadWakingUpUsingObjectExample {
    private static final Object LOCK = new Object();
    private static final ThreadAnthor b = new ThreadAnthor(LOCK);

    public static void main(String... args) throws InterruptedException {
        b.start();


        synchronized (LOCK) {
            log.debug("Waiting for ThreadAnthor to complete...");
            LOCK.wait();
            
            log.debug("ThreadAnthor has completed. Sum from that thread is: " + b.sum);
        }
    }

    static class ThreadAnthor extends Thread {
        final Object lock;
        int sum;
    
        public ThreadAnthor(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (this.lock) {
                log.debug("ThreadAnthor run...");
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

                log.debug("ThreadAnthor complete...");
                // 必须的
                this.lock.notify();
            }
            
        }
    }
}
