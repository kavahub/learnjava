package io.github.kavahub.learnjava.counter;

import java.util.concurrent.Callable;

/**
 * 
 * {@link SemaphoreCounter} 计数器回调
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SemaphoreCounterCallable implements Callable<Integer> {

    private final SemaphoreCounter counter;
    
    public SemaphoreCounterCallable(SemaphoreCounter counter) {
        this.counter = counter;
    }
    
    @Override
    public Integer call() throws Exception {
        counter.incrementCounter();
        return counter.getCounter();
    }
    
}
