package io.github.kavahub.learnjava.counter;

import java.util.concurrent.Callable;

/**
 * 
 * {@link ReentrantReadWriteLockCounter} 计数器回调
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ReentranReadWriteLockCounterCallable implements Callable<Integer> {

    private final ReentrantReadWriteLockCounter counter;

    public ReentranReadWriteLockCounterCallable(ReentrantReadWriteLockCounter counter) {
        this.counter = counter;
    }
    
    @Override
    public Integer call() throws Exception {
        counter.incrementCounter();
        return counter.getCounter();
    }
    
}
