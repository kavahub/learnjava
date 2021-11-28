package io.github.kavahub.learnjava.counter;

/**
 * 
 * 对象锁计数器
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ObjectLockCounter {
    private int counter;
    private final Object lock = new Object();

    public ObjectLockCounter() {
        this.counter = 0;
    }

    public void incrementCounter() {
        synchronized (lock) {
            counter += 1;
        }
    }

    public int getCounter() {
        synchronized (lock) {
            return counter;
        }
    }   
}
