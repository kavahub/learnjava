package io.github.kavahub.learnjava.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link StampedLock} 是乐观锁，
 * 乐观锁的意思就是乐观地估计读的过程中大概率不会有写入，因此被称为乐观锁。反过来，悲观锁则是读的过程中拒绝有写入，
 * 也就是写入必须等待。显然乐观锁的并发效率更高，但一旦有小概率的写入导致读取的数据不一致，需要能检测出来，再读一遍就行。
 * 
 * <p>
 * 深入分析
 * {@link ReadWriteLock}，会发现它有个潜在的问题：如果有线程正在读，写线程需要等待读线程释放锁后才能获取写锁，
 * 即读的过程中不允许写，这是一种悲观的读锁
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
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

    /**
     * 使用悲观锁读
     * 
     * @param key
     * @return
     * @throws InterruptedException
     */
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

    /**
     * 使用乐观锁读
     * @param key
     * @return
     * @throws InterruptedException
     */
    private String readWithOptimisticLock(String key) throws InterruptedException {
        // 获得一个乐观读锁
        long stamp = lock.tryOptimisticRead();
        String value = map.get(key);

        // 检查乐观读锁后是否有其他写锁发生
        if (!lock.validate(stamp)) {
            // 有，使用悲观锁读
            return this.get(key);
        }
        return value;
    }

    /**
     * 一般情况下，控制台输出3个线程的信息，极少情况会输出4个线程信息， 这是因为乐观锁读方法，没有输出信息
     * @param args
     */
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
