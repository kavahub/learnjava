package io.github.kavahub.learnjava.lifecycle;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 线程状态 {@link Thread.State#BLOCKED} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class BlockedStateExample{
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new DemoThreadB());
        Thread t2 = new Thread(new DemoThreadB());
        
        t1.start();
        t2.start();
        
        Thread.sleep(1000);
        
        log.info(t2.getState().toString());
        System.exit(0);
    }
}

class DemoThreadB implements Runnable {
    @Override
    public void run() {
        commonResource();
    }
    
    /**
     * 注意静态方法
     */
    public static synchronized void commonResource() {
        while(true) {
            // Infinite loop to mimic heavy processing
            // Thread 't1' won't leave this method
            // when Thread 't2' enters this
        }
    }
}

