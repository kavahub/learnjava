package io.github.kavahub.learnjava;

/**
 * Thread.yield方法，译为线程让步。顾名思义，就是说当一个线程使用了这个方法之后，它就会把自己CPU执行的时间让掉，
 * 让自己或者其它的线程运行，注意是让自己或者其他线程运行，并不是单纯的让给其他线程。
 * 
 * <p>
 * yield()的作用是让步。它能让当前线程由“运行状态”进入到“就绪状态”，从而让其它具有相同优先级的等待线程获取执行权；
 * 但是，并不能保证在当前线程调用yield()之后，其它具有相同优先级的线程就一定能获得执行权；也有可能是当前线程又进入到“运行状态”继续运行！
 */
public class ThreadYieldExample {
    public static void main(String[] args) {
        Runnable r = () -> {
            int counter = 0;
            while (counter < 10) {
                System.out.println(Thread.currentThread().getName());
                counter++;
                Thread.yield();
            }
        };
        new Thread(r).start();
        new Thread(r).start();
    }
}
