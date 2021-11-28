package io.github.kavahub.learnjava;

/**
 * 
 * 线程死锁示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ThreadDeadExample {
    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void main(String args[]) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("ThreadA: Holding lock 1...");
                sleep();
                System.out.println("ThreadA: Waiting for lock 2...");

                synchronized (lock2) {
                    System.out.println("ThreadA: Holding lock 1 & 2...");
                }
            }
        });
        Thread threadB = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("ThreadB: Holding lock 2...");
                sleep();
                System.out.println("ThreadB: Waiting for lock 1...");

                synchronized (lock1) {
                    System.out.println("ThreadB: Holding lock 1 & 2...");
                }
            }
        });
        threadA.start();
        threadB.start();
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
