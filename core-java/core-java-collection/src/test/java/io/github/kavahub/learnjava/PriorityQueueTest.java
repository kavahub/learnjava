package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

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
