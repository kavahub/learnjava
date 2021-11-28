package io.github.kavahub.learnjava.counter;

import java.util.concurrent.Callable;

/**
 * 
 * {@link Counter}计数器回调
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CounterCallable implements Callable<Integer> {

    private final Counter counter;
    
    public CounterCallable(Counter counter) {
        this.counter = counter;
    }

    @Override
    public Integer call() throws Exception {
        counter.incrementCounter();
        return counter.getCounter();
    }
    
}
