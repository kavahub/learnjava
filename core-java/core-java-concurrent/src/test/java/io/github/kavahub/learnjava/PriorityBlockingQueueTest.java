package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * {@link PriorityBlockingQueue}
 * 是一个无界有序的阻塞队列。同样的该队列不支持插入null元素，同时不支持插入非 {@code Comparable} 的对象。
 * 它的迭代器并不保证队列保持任何特定的顺序，如果想要顺序遍历，考虑使用 {@code Arrays.sort(pq.toArray())} 。
 * 该类不保证同等优先级的元素顺序，如果你想要强制顺序，就需要考虑自定义顺序或者是  {@code Comparator} 使用第二个比较属性
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class PriorityBlockingQueueTest {
    @Test
    public void givenUnorderedValues_whenPolling_thenShouldOrderQueue() throws InterruptedException {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        ArrayList<Integer> polledElements = new ArrayList<>();

        // 有重复元素
        queue.add(1);
        queue.add(5);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);

        // drainTo方法再执行元素转移的时候，每转移一个元素都执行了dequeue方法对二叉堆进行了排序，
        // 所以drainTo最后返回的数组的排好序的。drainTo传入的数组会被队列元素覆盖，而且超出队列长度的位置都将被重置为null。
        queue.drainTo(polledElements);

        assertThat(polledElements.size()).isEqualTo(6);
        assertThat(polledElements).containsExactly(1, 2, 3, 4, 5, 5);
    }

    @Test
    public void whenPollingEmptyQueue_thenShouldBlockThread() throws InterruptedException {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        final Thread thread = new Thread(() -> {
            log.debug("Polling...");
            while (true) {
                try {
                    Integer poll = queue.take();
                    log.debug("Polled: " + poll);
                } catch (InterruptedException ignored) {
                }
            }
        });
        thread.start();

        Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        log.debug("Adding to queue");

        queue.addAll(newArrayList(1, 5, 6, 1, 2, 6, 7));
        Thread.sleep(TimeUnit.SECONDS.toMillis(2));

        assertTrue(queue.size() == 0);
    }
}
