package io.github.kavahub.learnjava.lifecycle;

/**
 * 
 * 线程状态 {@link Thread.State#RUNNABLE} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class RunnableStateExample implements Runnable {
    public static void main(String[] args) {
        Runnable runnable = new RunnableStateExample();
        Thread t = new Thread(runnable);
        t.start();
        System.out.println(t.getState());
    }
    
    @Override
    public void run() {
        
    }
    
}
