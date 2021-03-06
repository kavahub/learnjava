package io.github.kavahub.learnjava;

/**
 * 
 * 两个线程交替执行，使用 {@code Object#wait()} 和 {@code Object#notify()}
 *
 * @author PinWei Wan
 * @since 1.0.0
 * 
 * @see SemaphorePrintEvenOddExample
 */
public class WaitNotifyPrintEvenOddExample {
    public static void main(String... args) {
        Printer print = new Printer();
        Thread t1 = new Thread(new TaskEvenOdd(print, 10, false), "Odd");
        Thread t2 = new Thread(new TaskEvenOdd(print, 10, true), "Even");
        t1.start();
        t2.start();
    }
}

class TaskEvenOdd implements Runnable {
    private final int max;
    private final Printer print;
    private final boolean isEvenNumber;

    TaskEvenOdd(Printer print, int max, boolean isEvenNumber) {
        this.print = print;
        this.max = max;
        this.isEvenNumber = isEvenNumber;
    }

    @Override
    public void run() {
        int number = isEvenNumber ? 2 : 1;
        while (number <= max) {
            if (isEvenNumber) {
                print.printEven(number);
            } else {
                print.printOdd(number);
            }
            number += 2;
        }
    }
}

class Printer {
    private volatile boolean isOdd;

    synchronized void printEven(int number) {
        while (!isOdd) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + ":" + number);
        isOdd = false;
        notify();
    }

    synchronized void printOdd(int number) {
        while (isOdd) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + ":" + number);
        isOdd = true;
        notify();
    }

}

