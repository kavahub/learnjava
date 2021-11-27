package io.github.kavahub.learnjava.common.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.stream.IntStream;

import com.google.common.collect.MinMaxPriorityQueue;

import org.junit.jupiter.api.Test;

/**
 * {@link MinMaxPriorityQueue}类，它提供了一种常数时间复杂度的方式访问其最小和最大元素的数据结构。
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MinMaxPriorityQueueTest {
    @Test
    public void givenMinMaxPriorityQueue_whenAddElementToFull_thenShouldEvictGreatestItem() {
        // given
        MinMaxPriorityQueue<CustomClass> queue = MinMaxPriorityQueue
                // 指定比较器，如果没有指定那么使用自然排序
                .orderedBy(Comparator.comparing(CustomClass::getValue))
                // 指定队列大小, 每次队列的大小超过该值时，队列将根据其比较器（可能是刚刚添加的元素）自动删除其最大元素
                .maximumSize(10).create();

        // when
        IntStream.iterate(10, i -> i - 1).limit(10).forEach(i -> queue.add(new CustomClass(i)));

        // then
        assertThat(queue.peekFirst().getValue()).isEqualTo(1); // 检索最小的元素, 不会从队列中删除元素
        assertThat(queue.peekLast().getValue()).isEqualTo(10); // 检索最大的元素, 不会从队列中删除元素

        // and
        queue.add(new CustomClass(-1));

        // then
        assertThat(queue.peekFirst().getValue()).isEqualTo(-1);
        assertThat(queue.peekLast().getValue()).isEqualTo(9);

        // and
        queue.add(new CustomClass(100)); // 超过队列大小，丢弃最大元素
        assertThat(queue.peekFirst().getValue()).isEqualTo(-1);
        assertThat(queue.peekLast().getValue()).isEqualTo(9);

    }

    class CustomClass {
        private final Integer value;

        CustomClass(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "CustomClass{" + "value=" + value + '}';
        }
    }
}
