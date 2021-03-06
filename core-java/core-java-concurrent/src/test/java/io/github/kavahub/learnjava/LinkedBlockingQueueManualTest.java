package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * {@link LinkedBlockingQueue} 内部由单链表实现，只能从head取元素，从tail添加元素, 先进先出的顺序。
 * 添加元素和获取元素都有独立的锁，也就是说是读写分离的，读写操作可以并行执行
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class LinkedBlockingQueueManualTest {
    @Test
    public void givenThereIsExistingCollection_WhenAddedIntoQueue_ThenShouldContainElements() {
        Collection<Integer> elements = Arrays.asList(1, 2, 3, 4, 5);
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(elements);
        assertThat(linkedBlockingQueue).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    public void givenQueueIsEmpty_WhenAccessingTheQueue_ThenThreadBlocks() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>();
        executorService.submit(() -> {
            try {
                // 从队列中消费数据, 当队列为空时，线程阻塞
                linkedBlockingQueue.take();
                log.info("正在消费数据...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test
    public void givenProducerPutsElementInQueue_WhenConsumerAccessQueue_ThenItRetrieve()
            throws InterruptedException, ExecutionException {
        int element = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>();

        // 生产数据
        Runnable putTask = () -> {
            try {
                log.info("生产数据");
                linkedBlockingQueue.put(element);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // 消费数据
        Callable<Integer> takeTask = () -> {
            try {
                log.info("消费数据");
                return linkedBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        };

        executorService.submit(putTask);
        Future<Integer> returnElement = executorService.submit(takeTask);

        assertThat(returnElement.get().intValue(), is(equalTo(element)));

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }
}
