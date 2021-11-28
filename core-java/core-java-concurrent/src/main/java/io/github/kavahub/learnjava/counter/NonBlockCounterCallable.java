package io.github.kavahub.learnjava.counter;

import java.util.concurrent.Callable;

/**
 * 
 * {@link NonBlockCounter}计数器回调
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class NonBlockCounterCallable implements Callable<Integer> {

    private final NonBlockCounter counter;
    
    public NonBlockCounterCallable(NonBlockCounter counter) {
        this.counter = counter;
    }
    
    @Override
    public Integer call() throws Exception {
        counter.incrementCounter();
        return counter.getCounter();
    }
    
}
