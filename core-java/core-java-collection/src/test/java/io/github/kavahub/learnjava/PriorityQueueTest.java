package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link PriorityQueue} 示例
 * 
 * <p>
 * {@code PriorityQueue} 实现了 {@code Queue} 接口，不允许放入null元素；其通过堆实现，具体说是通过完全二叉树（complete binary tree）实现的小顶堆
 * （任意一个非叶子节点的权值，都不大于其左右子节点的权值），也就意味着可以通过数组来作为底层实现
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class PriorityQueueTest {
    @Test
  public void givenIntegerQueue_whenIntegersOutOfOrder_checkRetrievalOrderIsNatural() {

    PriorityQueue<Integer> integerQueue = new PriorityQueue<>();

    integerQueue.add(9);
    integerQueue.add(2);
    integerQueue.add(4);

    int first = integerQueue.poll();
    int second = integerQueue.poll();
    int third = integerQueue.poll();

    assertEquals(2, first);
    assertEquals(4, second);
    assertEquals(9, third);


  }

  @Test
  public void givenStringQueue_whenStringsAddedOutOfNaturalOrder_checkRetrievalOrderNatural() {

    PriorityQueue<String> stringQueue = new PriorityQueue<>();

    stringQueue.add("banana");
    stringQueue.add("apple");
    stringQueue.add("cherry");

    String first = stringQueue.poll();
    String second = stringQueue.poll();
    String third = stringQueue.poll();

    assertEquals("apple", first);
    assertEquals("banana", second);
    assertEquals("cherry", third);


  }
}
