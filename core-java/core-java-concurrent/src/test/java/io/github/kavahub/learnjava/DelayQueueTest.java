package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.primitives.Ints;

import org.junit.jupiter.api.Test;

/**
 * {@link DelayQueue} 是BlockingQueue的一种，所以它是线程安全的，
 * 特点就是插入Queue中的数据可以按照自定义的delay时间进行排序。 只有delay时间小于0的元素才能够被取出
 * 
 */
public class DelayQueueTest {
    @Test
    public void givenDelayQueue_whenProduceElement_thenShouldConsumeAfterGivenDelay() throws InterruptedException {
        // given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 2;
        int delayOfEachProducedMessageMilliseconds = 500;
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer = new DelayQueueProducer(queue, numberOfElementsToProduce,
                delayOfEachProducedMessageMilliseconds);

        // when
        executor.submit(producer);
        executor.submit(consumer);

        // then
        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();
        assertEquals(consumer.numberOfConsumedElements.get(), numberOfElementsToProduce);

    }

    @Test
    public void givenDelayQueue_whenProduceElementWithHugeDelay_thenConsumerWasNotAbleToConsumeMessageInGivenTime()
            throws InterruptedException {
        // given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 1;
        int delayOfEachProducedMessageMilliseconds = 10_000;
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer = new DelayQueueProducer(queue, numberOfElementsToProduce,
                delayOfEachProducedMessageMilliseconds);

        // when
        executor.submit(producer);
        executor.submit(consumer);

        // then
        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();
        assertEquals(consumer.numberOfConsumedElements.get(), 0);

    }

    @Test
    public void givenDelayQueue_whenProduceElementWithNegativeDelay_thenConsumeMessageImmediately()
            throws InterruptedException {
        // given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 1;
        int delayOfEachProducedMessageMilliseconds = -10_000;
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer = new DelayQueueProducer(queue, numberOfElementsToProduce,
                delayOfEachProducedMessageMilliseconds);

        // when
        executor.submit(producer);
        executor.submit(consumer);

        // then
        executor.awaitTermination(1, TimeUnit.SECONDS);
        executor.shutdown();
        assertEquals(consumer.numberOfConsumedElements.get(), 1);

    }

    /**
     * 
     */
    public static class DelayObject implements Delayed {
        private String data;
        private long startTime;

        DelayObject(String data, long delayInMilliseconds) {
            this.data = data;
            this.startTime = System.currentTimeMillis() + delayInMilliseconds;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = startTime - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o == null) {
                // null last
                return -1;
            }
            return Ints.saturatedCast(this.startTime - ((DelayObject) o).startTime);
        }

        @Override
        public String toString() {
            return "{" + "data='" + data + '\'' + ", startTime=" + startTime + '}';
        }
    }

    public static class DelayQueueProducer implements Runnable {
        private BlockingQueue<DelayObject> queue;
        private final Integer numberOfElementsToProduce;
        private final Integer delayOfEachProducedMessageMilliseconds;

        DelayQueueProducer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToProduce,
                Integer delayOfEachProducedMessageMilliseconds) {
            this.queue = queue;
            this.numberOfElementsToProduce = numberOfElementsToProduce;
            this.delayOfEachProducedMessageMilliseconds = delayOfEachProducedMessageMilliseconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < numberOfElementsToProduce; i++) {
                DelayObject object = new DelayObject(UUID.randomUUID().toString(),
                        delayOfEachProducedMessageMilliseconds);
                System.out.println("Put object = " + object);
                try {
                    queue.put(object);
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }

    public static class DelayQueueConsumer implements Runnable {
        private BlockingQueue<DelayObject> queue;
        private final Integer numberOfElementsToTake;
        final AtomicInteger numberOfConsumedElements = new AtomicInteger();

        DelayQueueConsumer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToTake) {
            this.queue = queue;
            this.numberOfElementsToTake = numberOfElementsToTake;
        }

        @Override
        public void run() {
            for (int i = 0; i < numberOfElementsToTake; i++) {
                try {
                    DelayObject object = queue.take();
                    numberOfConsumedElements.incrementAndGet();
                    System.out.println("Consumer take: " + object);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
