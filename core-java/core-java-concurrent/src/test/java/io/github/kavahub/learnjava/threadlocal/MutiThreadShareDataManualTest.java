package io.github.kavahub.learnjava.threadlocal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * 多线程间共享数据
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MutiThreadShareDataManualTest {
    private final static int threads = 10;
    private final static ExecutorService pool = Executors.newFixedThreadPool(threads);

    /**
     * 多线程共享数据，导致数据被其他线程篡改
     * 
     * @throws InterruptedException
     */
    @Test
    public void whenMutiThreadUpdate_thenDataTampered() throws InterruptedException {
        MyData shareData = new MyData();

        Stream.generate(() -> new Runnable() {
            @Override
            public void run() {
                shareData.setData(Thread.currentThread().getName() + "的数据");

                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "--->" + shareData.getData());
            }
        }).limit(threads).forEach(pool::execute);

        pool.shutdown();
        pool.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println(shareData.getHistory());
    }

    /**
     * 使用锁，保证共享数据不被其他线程篡改
     * 
     * @throws InterruptedException
     */
    @Test
    public void givenLock_whenMutiThreadUpdate_thenOk() throws InterruptedException {
        MyData shareData = new MyData();
        ReentrantLock lock = new ReentrantLock();

        Stream.generate(() -> new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    shareData.setData(Thread.currentThread().getName() + "的数据");

                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + "--->" + shareData.getData());
                } finally {
                    lock.unlock();
                }
            }
        }).limit(threads).forEach(pool::execute);

        pool.shutdown();
        pool.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println(shareData.getHistory());
    }

    /**
     * 使用 {@code ThreadLocal}，保证共享数据不被其他线程篡改
     * 
     * @throws InterruptedException
     */
    @Test
    public void givenThreadLocal_whenMutiThreadUpdate_thenOk() throws InterruptedException {
        ThreadLocal<MyData> shareData = new ThreadLocal<>();
        
        Stream.generate(() -> new Runnable() {
            @Override
            public void run() {
                shareData.set(new MyData());
                shareData.get().setData(Thread.currentThread().getName() + "的数据");

                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "--->" + shareData.get().getData());
            }
        }).limit(threads).forEach(pool::execute);

        pool.shutdown();
        pool.awaitTermination(60, TimeUnit.SECONDS);
        System.out.println(shareData.get());

        // 清理
        shareData.remove();
    }

    private class MyData {
        private List<String> history = new CopyOnWriteArrayList<>();
        private String data;

        public List<String> getHistory() {
            return history;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            history.add(data);
            this.data = data;
        }
    }
}
