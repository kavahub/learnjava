package io.github.kavahub.learnjava.lifecycle;

/**
 * 
 * 线程状态 {@link Thread.State#WAITING} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class WaitingStateExample implements Runnable {
    public static Thread t1;

    public static void main(String[] args) {
        t1 = new Thread(new WaitingStateExample());
        t1.start();
    }

    public void run() {
        Thread t2 = new Thread(new DemoThreadWS());
        t2.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}

class DemoThreadWS implements Runnable {
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        
        System.out.println(WaitingStateExample.t1.getState());
    }
}
