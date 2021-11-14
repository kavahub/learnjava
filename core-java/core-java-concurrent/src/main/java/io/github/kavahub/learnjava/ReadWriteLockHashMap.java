package io.github.kavahub.learnjava;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import lombok.extern.slf4j.Slf4j;

/**
 * @formatter:off
 * 读写锁ReentrantReadWriteLock，它表示两个锁，一个是读操作相关的锁，称为共享锁；一个是写相关的锁，称为排他锁
 * 现象：
 * > 在线程持有读锁的情况下，该线程不能取得写锁(因为获取写锁的时候，如果发现当前的读锁被占用，就马上获取失败，不管读锁是不是被当前线程持有)。
 * > 在线程持有写锁的情况下，该线程可以继续获取读锁（获取读锁时如果发现写锁被占用，只有写锁没有被当前线程占用的情况才会获取失败）。
 * > 一个线程要想同时持有写锁和读锁，必须先获取写锁再获取读锁；写锁可以“降级”为读锁；读锁不能“升级”为写锁。
 * 
 * 从日志分析
 * > read 与 read end之间可以存在其他的read，但不能存在write
 * > write 与 writed end之间不能存在write及read (调整put方法的睡眠时间就很明显)
 * @formatter:on
 * 

 */
@Slf4j
public class ReadWriteLockHashMap {
    private static Map<String, String> syncHashMap = new HashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public void put(String key, String value) throws InterruptedException {

        try {
            writeLock.lock();
            log.info(Thread.currentThread().getName() + " writing");
            syncHashMap.put(key, value);
            Thread.sleep(100);
        } finally {
            log.info(Thread.currentThread().getName() + " writing end");
            writeLock.unlock();
        }

    }

    public String get(String key) {
        try {
            readLock.lock();
            log.info(Thread.currentThread().getName() + " reading");
            return syncHashMap.get(key);
        } finally {
            log.info(Thread.currentThread().getName() + " reading end");
            readLock.unlock();
        }
    }

    public String remove(String key) {
        try {
            writeLock.lock();
            return syncHashMap.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    public boolean containsKey(String key) {
        try {
            readLock.lock();
            return syncHashMap.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }

    boolean isWriteLockAvailable() {
        return writeLock.tryLock();
    }

    boolean isReadLockAvailable() {
        return readLock.tryLock();
    }

    public static void main(String[] args) throws InterruptedException {

        final int threadCount = 4;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        ReadWriteLockHashMap object = new ReadWriteLockHashMap();

        service.execute(new Thread(new Writer(object), "Writer1"));
        service.execute(new Thread(new Writer(object), "Writer2"));
        service.execute(new Thread(new Reader(object), "Reader1"));
        service.execute(new Thread(new Reader(object), "Reader2"));

        service.shutdown();
    }

    private static class Reader implements Runnable {

        ReadWriteLockHashMap object;

        Reader(ReadWriteLockHashMap object) {
            this.object = object;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                object.get("key" + i);
            }
        }
    }

    private static class Writer implements Runnable {

        ReadWriteLockHashMap object;

        public Writer(ReadWriteLockHashMap object) {
            this.object = object;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    object.put("key" + i, "value" + i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
