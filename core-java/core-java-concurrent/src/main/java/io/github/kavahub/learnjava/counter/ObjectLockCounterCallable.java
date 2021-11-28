package io.github.kavahub.learnjava.counter;

import java.util.concurrent.Callable;

/**
 * 
 * {@link ObjectLockCounter}计数器回调
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ObjectLockCounterCallable implements Callable<Integer> {

    private final ObjectLockCounter counter;
    
    public ObjectLockCounterCallable(ObjectLockCounter counter) {
        this.counter = counter;
    }
    
    @Override
    public Integer call() throws Exception {
        counter.incrementCounter();
        return counter.getCounter();
    }
    
}
