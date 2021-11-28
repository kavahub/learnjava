package io.github.kavahub.learnjava.counter;

import java.util.concurrent.Callable;

/**
 * 
 * {@link AtomicCounter}计数器回调
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class AtomicCounterCallable implements Callable<Integer> {

    private final AtomicCounter counter;
    
    public AtomicCounterCallable(AtomicCounter counter) {
        this.counter = counter;
    }

    @Override
    public Integer call() throws Exception {
        counter.incrementCounter();
        return counter.getCounter();
    }
    
}
