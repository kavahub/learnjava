package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * {@link ConcurrentLinkedQueue} 一个基于链接节点的无界线程安全的队列，先进先出原则。新元素从队列尾部插入，
 * 而获取队列元素，则需要从队列头部获取。队列元素中不可以放置null元素
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ConcurrentLinkedQueueManualTest {
    @Test
    public void givenThereIsExistingCollection_WhenAddedIntoQueue_ThenShouldContainElements() {
        Collection<Integer> elements = Arrays.asList(1, 2, 3, 4, 5);
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>(elements);
        assertThat(concurrentLinkedQueue).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    public void givenQueueIsEmpty_WhenAccessingTheQueue_ThenQueueReturnsNull() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        executorService.submit(() -> assertNull(concurrentLinkedQueue.poll()));
        TimeUnit.SECONDS.sleep(1);
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    @Test
    public void givenProducerOffersElementInQueue_WhenConsumerPollsQueue_ThenItRetrievesElement() throws Exception {
        int element = 1;

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        // offer方法将指定元素插入此队列的尾部
        Runnable offerTask = () -> concurrentLinkedQueue.offer(element);

        Callable<Integer> pollTask = () -> {
            // peek方法获取但不移除此队列的头；如果此队列为空，则返回 null
            while (concurrentLinkedQueue.peek() != null) {
                // poll方法获取并移除此队列的头，如果此队列为空，则返回 null
                return concurrentLinkedQueue.poll().intValue();
            }
            return null;
        };

        executorService.submit(offerTask);
        TimeUnit.SECONDS.sleep(1);

        Future<Integer> returnedElement = executorService.submit(pollTask);
        assertThat(returnedElement.get().intValue(), is(equalTo(element)));
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }
}
