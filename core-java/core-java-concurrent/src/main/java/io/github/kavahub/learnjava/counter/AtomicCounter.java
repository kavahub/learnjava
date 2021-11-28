package io.github.kavahub.learnjava.counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 原子计数器
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class AtomicCounter {
    private final AtomicInteger counter = new AtomicInteger();
    
    public AtomicCounter() {}
    
    public void incrementCounter() {
        counter.incrementAndGet();
    }
    
    public synchronized int getCounter() {
        return counter.get();
    }
}
