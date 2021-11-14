package io.github.kavahub.learnjava.lifecycle;

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
