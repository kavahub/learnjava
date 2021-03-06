package io.github.kavahub.learnjava.counter;

import java.util.concurrent.Callable;

/**
 * 
 * {@link UnsafeCounter}计数器回调
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class UnsafeCounterCallable implements Callable<Integer> {

    private final UnsafeCounter counter;
    
    public UnsafeCounterCallable(UnsafeCounter counter) {
        this.counter = counter;
    }
    
    @Override
    public Integer call() throws Exception {
        counter.incrementCounter();
        return counter.getCounter();
    }
    
}
