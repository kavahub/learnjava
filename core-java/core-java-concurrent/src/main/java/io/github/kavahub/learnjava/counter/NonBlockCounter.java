package io.github.kavahub.learnjava.counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 非阻塞计数器
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class NonBlockCounter {
    private final AtomicInteger counter = new AtomicInteger(0);

    
    public void incrementCounter() {
        int existingValue = 0;
        int newValue = 0;
        do{
            existingValue = getCounter();
            newValue = existingValue + 1;
        }while(!counter.compareAndSet(existingValue, newValue));

    }

    public int getCounter() {
        return counter.get();
    }
}
