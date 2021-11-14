package io.github.kavahub.learnjava;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

import lombok.extern.slf4j.Slf4j;

/**
 * StampedLock和ReadWriteLock相比，改进之处在于：读的过程中也允许获取写锁后写入！这样一来，
 * 我们读的数据就可能不一致，所以，需要一点额外的代码来判断读的过程中是否有写入，这种读锁是一种乐观锁
 */
@Slf4j
public class StampedLockExample {
    private Map<String, String> map = new HashMap<>();

    private final StampedLock lock = new StampedLock();

    public void put(String key, String value) throws InterruptedException {
        long stamp = lock.writeLock();

        try {
            log.info(Thread.currentThread().getName() + " acquired the write lock with stamp " + stamp);
            map.put(key, value);
        } finally {
            lock.unlockWrite(stamp);
            log.info(Thread.currentThread().getName() + " unlocked the write lock with stamp " + stamp);
        }
    }

    public String get(String key) throws InterruptedException {
        long stamp = lock.readLock();
        log.info(Thread.currentThread().getName() + " acquired the read lock with stamp " + stamp);
        try {
            Thread.sleep(5000);
            return map.get(key);

        } finally {
            lock.unlockRead(stamp);
            log.info(Thread.currentThread().getName() + " unlocked the read lock with stamp " + stamp);

        }

    }

    private String readWithOptimisticLock(String key) throws InterruptedException {
        long stamp = lock.tryOptimisticRead();
        String value = map.get(key);

        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                Thread.sleep(5000);
                return map.get(key);

            } finally {
                lock.unlock(stamp);
                log.info(Thread.currentThread().getName() + " unlocked the read lock with stamp " + stamp);

            }
        }
        return value;
    }

    public static void main(String[] args) {
        final int threadCount = 4;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        StampedLockExample object = new StampedLockExample();

        Runnable writeTask = () -> {

            try {
                object.put("key1", "value1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable readTask = () -> {

            try {
                object.get("key1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable readOptimisticTask = () -> {

            try {
                object.readWithOptimisticLock("key1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        service.submit(writeTask);
        service.submit(writeTask);
        service.submit(readTask);
        service.submit(readOptimisticTask);

        service.shutdown();

    }
}
