package io.github.kavahub.learnjava.counter;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounter {
    private int counter;
    private final ReentrantLock reLock = new ReentrantLock(true);

    public ReentrantLockCounter() {
        this.counter = 0;
    }

    public int incrementCounter() {
        reLock.lock();
        try {
            counter += 1;
            return counter;
        } finally {
            reLock.unlock();
        }
    }

    public int getCounter() {
        return counter;
    }
}
