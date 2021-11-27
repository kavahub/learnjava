package io.github.kavahub.learnjava.enhance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link CharStackWithLinkedList} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CharStackWithLinkedListTest {
    @Test
    public void whenCharStackIsCreated_thenItHasSize0() {

        CharStackWithLinkedList charStack = new CharStackWithLinkedList();

        assertEquals(0, charStack.size());
    }

    @Test
    public void givenEmptyCharStack_whenElementIsPushed_thenStackSizeisIncreased() {

        CharStackWithLinkedList charStack = new CharStackWithLinkedList();

        charStack.push('A');

        assertEquals(1, charStack.size());
    }

    @Test
    public void givenCharStack_whenElementIsPoppedFromStack_thenElementIsRemovedAndSizeChanges() {

        CharStackWithLinkedList charStack = new CharStackWithLinkedList();
        charStack.push('A');

        char element = charStack.pop();

        assertEquals('A', element);
        assertEquals(0, charStack.size());
    }

    @Test
    public void givenCharStack_whenElementIsPeeked_thenElementIsNotRemovedAndSizeDoesNotChange() {
        CharStackWithLinkedList charStack = new CharStackWithLinkedList();
        charStack.push('A');

        char element = charStack.peek();

        assertEquals('A', element);
        assertEquals(1, charStack.size());
    }
}
