package io.github.kavahub.learnjava.lifecycle;

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
