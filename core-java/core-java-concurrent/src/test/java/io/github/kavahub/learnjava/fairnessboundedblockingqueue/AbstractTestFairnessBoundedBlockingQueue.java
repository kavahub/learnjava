package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTestFairnessBoundedBlockingQueue {
    private final static int MAX_SIZE = 10;
    protected abstract Queue<Integer> newInstance(int size);

    public void giveSize_whenOffer_thenSuccess() {
        Queue<Integer> queue = newInstance(MAX_SIZE);
        for (int i = 0; i < MAX_SIZE; i++) {
            assertTrue(queue.offer(i));
        }

        assertThat(queue.size()).isEqualTo(MAX_SIZE);
    }

    public void giveFullQueue_whenPoll_thenSuccess() {
        Queue<Integer> queue = newInstance(MAX_SIZE);
        for (int i = 0; i < MAX_SIZE; i++) {
            queue.offer(i);
        }

        for (int i = 0; i < MAX_SIZE; i++) {
            assertThat(queue.poll()).isEqualTo(i);
        }

        assertThat(queue.size()).isEqualTo(0);
    }

    public void giveQueue_whenOfferAndPoll_thenSuccess() throws InterruptedException {
        Queue<Integer> queue = newInstance(MAX_SIZE);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable producer = () -> {
            for(int i = 0; i < 100; i++) {
                queue.offer(i);
                assertThat(queue.size()).isBetween(0, MAX_SIZE -1);
            }
            System.out.println("Producer has completed");
        };

        Runnable consumer = () -> {
            for(int i = 0; i < 100; i++) {
                assertThat(queue.poll()).isEqualTo(i);
                assertThat(queue.size()).isBetween(0, MAX_SIZE - 1);
            }
            System.out.println("Consumer has completed");
        };

        executorService.submit(producer);
        executorService.submit(consumer);
        
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        assertThat(queue.size()).isEqualTo(0);

    }

    public void giveFullQueue_whenOffer_thenWaiting() throws InterruptedException {
        Queue<Integer> queue = newInstance(MAX_SIZE);
        Runnable runnable = () -> {
            for (int i = 0; i < MAX_SIZE; i++) {
                queue.offer(i);
            }

            // 阻塞
            queue.offer(11);
        };

        Thread thread = new Thread(runnable);
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        assertThat(thread.getState()).isEqualTo(Thread.State.WAITING);

        thread.interrupt();
        assertThat(queue.size()).isEqualTo(MAX_SIZE);
    }


    public void giveEmptyQueue_whenPoll_thenWaiting() throws InterruptedException {
        Queue<Integer> queue = newInstance(MAX_SIZE);
        Runnable runnable = () -> {
            for (int i = 0; i < MAX_SIZE; i++) {
                queue.offer(i);
            }

            for (int i = 0; i < MAX_SIZE; i++) {
                queue.poll();
            }

            // 阻塞
            queue.poll();
        };

        Thread thread = new Thread(runnable);
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        assertThat(thread.getState()).isEqualTo(Thread.State.WAITING);

        thread.interrupt();
        assertThat(queue.size()).isEqualTo(0);
    }

    public void giveQueue_whenMutiThreadOffer_thenLimit() throws InterruptedException {
        final int size = 5;
        final int threads = size * 2;
        Queue<Integer> queue = newInstance(size);
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch startThread = new CountDownLatch(1);

        for (int i = 0; i < size * 2; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    startThread.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + ":" + queue.offer(index));
            });
        }

        TimeUnit.SECONDS.sleep(1);
        startThread.countDown();

        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.SECONDS);
        assertThat(queue.size()).isEqualTo(size);
    }
}
