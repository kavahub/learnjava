package io.github.kavahub.learnjava.demo.counter;

import java.util.concurrent.Callable;

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
