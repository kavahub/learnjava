package io.github.kavahub.learnjava;

import java.time.Instant;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

/**
 * 
 * 多个线程同时执行
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ThreadsStartAtSameTimeExample {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        usingCountDownLatch();

        Thread.sleep(30);

        usingCyclicBarrier();

        Thread.sleep(30);

        usingPhaser();

    }

    private static void usingCountDownLatch() throws InterruptedException {
        System.out.println("===============================================");
        System.out.println("        >>> Using CountDownLatch <<<<");
        System.out.println("===============================================");

        CountDownLatch latch = new CountDownLatch(1);

        WorkerWithCountDownLatch worker1 = new WorkerWithCountDownLatch("Worker with latch 1", latch);
        WorkerWithCountDownLatch worker2 = new WorkerWithCountDownLatch("Worker with latch 2", latch);

        worker1.start();
        worker2.start();

        Thread.sleep(30);// simulation of some actual work

        System.out.println("-----------------------------------------------");
        System.out.println(" Now release the latch:");
        System.out.println("-----------------------------------------------");
        latch.countDown();
    }

    private static void usingCyclicBarrier() throws BrokenBarrierException, InterruptedException {
        System.out.println("\n===============================================");
        System.out.println("        >>> Using CyclicBarrier <<<<");
        System.out.println("===============================================");

        CyclicBarrier barrier = new CyclicBarrier(3);

        WorkerWithCyclicBarrier worker1 = new WorkerWithCyclicBarrier("Worker with barrier 1", barrier);
        WorkerWithCyclicBarrier worker2 = new WorkerWithCyclicBarrier("Worker with barrier 2", barrier);

        worker1.start();
        worker2.start();

        Thread.sleep(20);// simulation of some actual work

        System.out.println("-----------------------------------------------");
        System.out.println(" Now open the barrier:");
        System.out.println("-----------------------------------------------");
        barrier.await();
    }

    private static void usingPhaser() throws InterruptedException {
        System.out.println("\n===============================================");
        System.out.println("        >>> Using Phaser <<<");
        System.out.println("===============================================");

        Phaser phaser = new Phaser();
        phaser.register();

        WorkerWithPhaser worker1 = new WorkerWithPhaser("Worker with phaser 1", phaser);
        WorkerWithPhaser worker2 = new WorkerWithPhaser("Worker with phaser 2", phaser);

        worker1.start();
        worker2.start();

        Thread.sleep(20);// simulation of some actual work

        System.out.println("-----------------------------------------------");
        System.out.println(" Now open the phaser barrier:");
        System.out.println("-----------------------------------------------");
        phaser.arriveAndAwaitAdvance();
    }

    /**
     * CountDownLatch[lætʃ]可以理解就是个计数器，只能减不能加，同时它还有个门闩的作用，当计数器不为0时，门闩是锁着的；当计数器减到0时，门闩就打开了。
     */
    public static class WorkerWithCountDownLatch extends Thread {
        private CountDownLatch latch;

        public WorkerWithCountDownLatch(String name, CountDownLatch latch) {
            this.latch = latch;
            setName(name);
        }

        @Override
        public void run() {
            try {
                System.out.printf("[ %s ] created, blocked by the latch\n", getName());
                latch.await();
                System.out.printf("[ %s ] starts at: %s\n", getName(), Instant.now());
                // do actual work here...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * CyclicBarrier[saɪklɪk][bæriə(r)](篱栅)可以使一定数量的线程反复地在栅栏位置处汇集。当线程到达栅栏位置时将调用await方法，
     * 这个方法将阻塞直到所有线程都到达栅栏位置。如果所有线程都到达栅栏位置，那么栅栏将打开，此时所有的线程都将被释放， 而栅栏将被重置以便下次使用。
     */
    public static class WorkerWithCyclicBarrier extends Thread {
        private CyclicBarrier barrier;

        public WorkerWithCyclicBarrier(String name, CyclicBarrier barrier) {
            this.barrier = barrier;
            this.setName(name);
        }

        @Override
        public void run() {
            try {
                System.out.printf("[ %s ] created, blocked by the barrier\n", getName());
                barrier.await();
                System.out.printf("[ %s ] starts at: %s\n", getName(), Instant.now());
                // do actual work here...
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Phaser[/ˈfeɪzə/](阶段器)用来解决控制多个线程分阶段共同完成任务的情景问题
     */
    public static class WorkerWithPhaser extends Thread {
        private Phaser phaser;

        public WorkerWithPhaser(String name, Phaser phaser) {
            this.phaser = phaser;
            phaser.register();
            setName(name);
        }

        @Override
        public void run() {
            try {
                System.out.printf("[ %s ] created, blocked by the phaser\n", getName());
                phaser.arriveAndAwaitAdvance();
                System.out.printf("[ %s ] starts at: %s\n", getName(), Instant.now());
                // do actual work here...
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

}
