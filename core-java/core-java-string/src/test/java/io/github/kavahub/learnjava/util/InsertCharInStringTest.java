package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link InsertCharacterInString} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class InsertCharInStringTest {
    @Test
    public void whenInsertCharAtIndexUsingSubstring_thenSuccess() {
        assertEquals("abcme", InsertCharacterInString.insertCharSubstring("abcde", 'm', 3));
    }

    @Test
    public void whenInsertCharAtIndexUsingCharArray_thenSuccess() {
        assertEquals("abcme", InsertCharacterInString.insertCharUsingCharArray("abcde", 'm', 3));
    }

    @Test
    public void whenInsertCharAtIndexUsingStringBuilder_thenSuccess() {
        assertEquals("abcme", InsertCharacterInString.insertCharStringBuilder("abcde", 'm', 3));
    }

    @Test
    public void whenInsertCharAtIndexUsingStringBuffer_thenSuccess() {
        assertEquals("abcme", InsertCharacterInString.insertCharStringBuffer("abcde", 'm', 3));
    }    
}
