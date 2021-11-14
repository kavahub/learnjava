package io.github.kavahub.learnjava.demo.counter;

import java.util.concurrent.Callable;

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
