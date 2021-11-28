package io.github.kavahub.learnjava.counter;

/**
 * 
 * 同步计数器
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class Counter {
    private volatile int counter;
    
    public Counter() {
        this.counter = 0;
    }
    
    public synchronized void incrementCounter() {
        counter += 1;
    }
    
    public int getCounter() {
        return counter;
    }
}
