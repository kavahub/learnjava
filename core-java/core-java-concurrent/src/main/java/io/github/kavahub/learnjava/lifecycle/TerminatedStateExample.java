package io.github.kavahub.learnjava.lifecycle;

/**
 * 
 * 线程状态 {@link Thread.State#TERMINATED} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class TerminatedStateExample implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new TerminatedStateExample());
        t1.start();
        Thread.sleep(1000);
        System.out.println(t1.getState());
    }
    
    @Override
    public void run() {
        // No processing in this block
    }
    
}
