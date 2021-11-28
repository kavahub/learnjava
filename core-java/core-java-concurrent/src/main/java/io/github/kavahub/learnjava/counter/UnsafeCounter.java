package io.github.kavahub.learnjava.counter;

/**
 * 
 * 不安全计数器
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class UnsafeCounter {
    private int counter;
    
    public int getCounter() {
        return counter;
    }
    
    public void incrementCounter() {
        counter++;
    }
}
