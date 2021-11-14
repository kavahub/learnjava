package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * Semaphore(信号量)：是一种计数器，用来保护一个或者多个共享资源的访问。
 * 如果线程要访问一个资源就必须先获得信号量。如果信号量内部计数器大于0，信号量减1，
 * 然后允许共享这个资源；否则，如果信号量的计数器等于0，
 * 信号量将会把线程置入休眠直至计数器大于0.当信号量使用完时，必须释放
 */
public class SemaphoreTest {

    @Test
    public void givenSemaphoreZero_whenRelease_thenGetQueueLength() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        assertThat(semaphore.getQueueLength()).isEqualTo(0);

        semaphore.release();
        assertThat(semaphore.getQueueLength()).isEqualTo(0);

        semaphore.release();
        assertThat(semaphore.getQueueLength()).isEqualTo(0);
    }

    @Test
    public void givenSemaphoreZero_whenTreadAcquire_thenGetQueueLength() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    int index = 1;
                    while(true) {
                        semaphore.acquire();
                        System.out.printf("获取到资源 %s 次\n", index++);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } 
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    for(int i = 0; i <= 1; i++) {
                        TimeUnit.MILLISECONDS.sleep(100);
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            }
        };

        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(2);
    }
}
