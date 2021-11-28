package io.github.kavahub.learnjava.lifecycle;

/**
 * 
 * 线程状态 {@link Thread.State#NEW} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class NewStateExample implements Runnable {
    public static void main(String[] args) {
        Runnable runnable = new NewStateExample();
        Thread t = new Thread(runnable);
        System.out.println(t.getState());
    }
    
    @Override
    public void run() {
        
    }
    
}
