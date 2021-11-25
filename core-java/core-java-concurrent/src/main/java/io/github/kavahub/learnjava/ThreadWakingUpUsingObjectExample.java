package io.github.kavahub.learnjava;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

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
                // 必须的
                this.lock.notify();
            }
            
        }
    }
}
