package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link TransferQueue} 继承了BlockingQueue（BlockingQueue又继承了Queue）并扩展了一些新方法。BlockingQueue（和Queue）是Java
 * 5中加入的接口， 它是指这样的一个队列：当生产者向队列添加元素但队列已满时，生产者会被阻塞；当消费者从队列移除元素但队列为空时，消费者会被阻塞。
 * 
 * TransferQueue则更进一步，生产者会一直阻塞直到所添加到队列的元素被某一个消费者所消费（不仅仅是添加到队列里就完事）。
 * 新添加的transfer方法用来实现这种约束。顾名思义，阻塞就是发生在元素从一个线程transfer到另一个线程的过程中，
 * 它有效地实现了元素在线程之间的传递（以建立Java内存模型中的happens-before关系的方式）。
 * 
 * TransferQueue还包括了其他的一些方法：两个tryTransfer方法，一个是非阻塞的，
 * 另一个带有timeout参数设置超时时间的。还有两个辅助方法hasWaitingConsumer()和getWaitingConsumerCount()。
 * 
 */
@Slf4j
public class TransferQueueManualTest {
    @Test
    public void whenMultipleConsumersAndProducers_thenProcessAllMessages() throws InterruptedException {
        // given
        TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        ExecutorService exService = Executors.newFixedThreadPool(3);
        Producer producer1 = new Producer(transferQueue, "1", 3);
        Producer producer2 = new Producer(transferQueue, "2", 3);
        Consumer consumer1 = new Consumer(transferQueue, "1", 3);
        Consumer consumer2 = new Consumer(transferQueue, "2", 3);

        // when
        exService.execute(producer1);
        exService.execute(producer2);
        exService.execute(consumer1);
        exService.execute(consumer2);

        // then
        exService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        exService.shutdown();

        // 生产者运行完成
        assertEquals(producer1.numberOfProducedMessages.intValue(), 3);
        assertEquals(producer2.numberOfProducedMessages.intValue(), 3);
    }

    @Test
    public void whenUseOneConsumerAndOneProducer_thenShouldProcessAllMessages() throws InterruptedException {
        // given
        TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        ExecutorService exService = Executors.newFixedThreadPool(3);
        Producer producer = new Producer(transferQueue, "1", 6);
        Consumer consumer = new Consumer(transferQueue, "1", 3);
        Consumer consumer1 = new Consumer(transferQueue, "2", 3);

        // when
        exService.execute(producer);
        exService.execute(consumer);
        exService.execute(consumer1);

        // then
        exService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        exService.shutdown();

        assertEquals(producer.numberOfProducedMessages.intValue(), 6);
        assertEquals(consumer.numberOfConsumedMessages.intValue(), 3);
    }

    @Test
    public void whenUseOneProducerAndNoConsumers_thenShouldFailWithTimeout() throws InterruptedException {
        // given
        TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        ExecutorService exService = Executors.newFixedThreadPool(2);
        Producer producer = new Producer(transferQueue, "1", 3);

        // when
        exService.execute(producer);

        // then
        exService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        exService.shutdown();

        assertEquals(producer.numberOfProducedMessages.intValue(), 0);
    }

    public static class Consumer implements Runnable {
        private final TransferQueue<String> transferQueue;
        private final String name;
        final int numberOfMessagesToConsume;
        final AtomicInteger numberOfConsumedMessages = new AtomicInteger();

        Consumer(TransferQueue<String> transferQueue, String name, int numberOfMessagesToConsume) {
            this.transferQueue = transferQueue;
            this.name = name;
            this.numberOfMessagesToConsume = numberOfMessagesToConsume;
        }

        @Override
        public void run() {
            for (int i = 0; i < numberOfMessagesToConsume; i++) {
                try {
                    log.debug("Consumer: " + name + " is waiting to take element...");
                    String element = transferQueue.take();
                    longProcessing(element);
                    log.debug("Consumer: " + name + " received element: " + element);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void longProcessing(String element) throws InterruptedException {
            numberOfConsumedMessages.incrementAndGet();
            Thread.sleep(500);
        }
    }

    public static class Producer implements Runnable {

        private final TransferQueue<String> transferQueue;
        private final String name;
        final Integer numberOfMessagesToProduce;
        final AtomicInteger numberOfProducedMessages = new AtomicInteger();

        Producer(TransferQueue<String> transferQueue, String name, Integer numberOfMessagesToProduce) {
            this.transferQueue = transferQueue;
            this.name = name;
            this.numberOfMessagesToProduce = numberOfMessagesToProduce;
        }

        @Override
        public void run() {
            for (int i = 0; i < numberOfMessagesToProduce; i++) {
                try {
                    log.debug("Producer: " + name + " is waiting to transfer...");
                    boolean added = transferQueue.tryTransfer("A" + i, 4000, TimeUnit.MILLISECONDS);
                    if (added) {
                        numberOfProducedMessages.incrementAndGet();
                        log.debug("Producer: " + name + " transferred element: A" + i);
                    } else {
                        log.debug("can not add an element due to the timeout");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
