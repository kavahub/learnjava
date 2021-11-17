package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.junit.jupiter.api.Test;

/**
 * Stack继承自Vector。底层是通过数组实现的
 */
public class StackTest {
    @Test
    public void givenStack_whenPushPopPeek_thenWorkRight() {
        Stack<String> namesStack = new Stack<>();

        namesStack.push("Bill Gates");
        namesStack.push("Elon Musk");

        assertEquals("Elon Musk", namesStack.peek());
        assertEquals("Elon Musk", namesStack.pop());
        assertEquals("Bill Gates", namesStack.pop());

        assertEquals(0, namesStack.size());
    }


    @Test
    public void givenConcurrentLinkedDeque_whenPushPopPeek_thenWorkRight() {
        ConcurrentLinkedDeque<String> namesStack = new ConcurrentLinkedDeque<>();

        namesStack.push("Bill Gates");
        namesStack.push("Elon Musk");

        assertEquals("Elon Musk", namesStack.peek());
        assertEquals("Elon Musk", namesStack.pop());
        assertEquals("Bill Gates", namesStack.pop());

        assertEquals(0, namesStack.size());
    }
}
