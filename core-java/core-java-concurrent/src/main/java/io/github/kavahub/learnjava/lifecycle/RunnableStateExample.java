package io.github.kavahub.learnjava.lifecycle;

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
