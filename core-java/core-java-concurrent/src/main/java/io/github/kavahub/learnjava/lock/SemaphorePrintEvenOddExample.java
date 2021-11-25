package io.github.kavahub.learnjava.lock;

import java.util.concurrent.Semaphore;

/**
 * {@link Semaphore} 示例。实现两个线程交替执行
 */
public class SemaphorePrintEvenOddExample {
    public static void main(String[] args) {
        SharedPrinter sp = new SharedPrinter();
        Thread odd = new Thread(new Odd(sp, 10), "Odd");
        // 偶数
        Thread even = new Thread(new Even(sp, 10), "Even");

        odd.start();
        even.start();
    }
}

class SharedPrinter {

    // 偶数，注意初始化0
    private final Semaphore semEven = new Semaphore(0);
    // 奇数
    private final Semaphore semOdd = new Semaphore(1);

    // 偶数
    void printEvenNum(int num) {
        try {
            semEven.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + ":" + num);
        semOdd.release();
    }

    // 奇数
    void printOddNum(int num) {
        try {
            semOdd.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + ":" + num);
        semEven.release();
    }
}

// 偶数
class Even implements Runnable {
    private final SharedPrinter sp;
    private final int max;

    Even(SharedPrinter sp, int max) {
        this.sp = sp;
        this.max = max;
    }

    @Override
    public void run() {
        for (int i = 2; i <= max; i = i + 2) {
            sp.printEvenNum(i);
        }
    }
}

// 奇数
class Odd implements Runnable {
    private SharedPrinter sp;
    private int max;

    Odd(SharedPrinter sp, int max) {
        this.sp = sp;
        this.max = max;
    }

    @Override
    public void run() {
        for (int i = 1; i <= max; i = i + 2) {
            sp.printOddNum(i);
        }
    }
}
