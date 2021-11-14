package io.github.kavahub.learnjava.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import io.github.kavahub.learnjava.demo.futuretask.Connection;
import io.github.kavahub.learnjava.demo.futuretask.ConnectionPool;

public class FutureTaskConnectionPoolManualTest {
    private static final int POOL_SIZE = 10;
    /**
     * 有限的连接池，但很多线程获取连接
     * @throws InterruptedException
     */
    @Test
    public void givenPoolLimit_whenManyThreadGetConnection_thenOK() throws InterruptedException {
        final ConnectionPool pool = new ConnectionPool();

        // 创建线程池获取连接
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                int key = ThreadLocalRandom.current().nextInt(POOL_SIZE);
                Connection con = pool.getConnection(String.valueOf(key));
                con.use();
            });
        }

        executor.awaitTermination(60, TimeUnit.SECONDS);
    }

    @Test
    public void givenPoolLimit_whenManyThreadCreateConnect_thenOK() throws InterruptedException {
        final ConnectionPool pool = new ConnectionPool();

        // 使用大量线程，创建少数连接
        CountDownLatch lath = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            executor.submit(() -> {
                try {
                    lath.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int key = ThreadLocalRandom.current().nextInt(3);
                pool.getConnection(String.valueOf(key));
                //new Connection(String.valueOf(key));
            });
        }

        try {
            TimeUnit.SECONDS.sleep(3);
            
            lath.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        executor.awaitTermination(3, TimeUnit.SECONDS);
    }
}
