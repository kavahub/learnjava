package io.github.kavahub.learnjava.counter;

import java.util.concurrent.Callable;

/**
 * 
 * {@link ReentrantLockCounter} 计数器回调
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ReentrantLockCounterCallable implements Callable<Integer> {

    private final ReentrantLockCounter counter;
    
    public ReentrantLockCounterCallable(ReentrantLockCounter counter) {
        this.counter = counter;
    }
    
    @Override
    public Integer call() throws Exception {
        return counter.incrementCounter();
    }
    
}
