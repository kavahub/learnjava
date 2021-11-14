package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AppendCharAtPositionXTest {
    private String word = "Titanc";
    private char letter = 'i';

    @Test
    public void whenUsingCharacterArrayAndCharacterAddedAtBeginning_shouldAddCharacter() {
        assertEquals("iTitanc", AppendCharAtPositionX.addCharUsingCharArray(word, letter, 0));
    }

    @Test
    public void whenUsingSubstringAndCharacterAddedAtBeginning_shouldAddCharacter() {
        assertEquals("iTitanc", AppendCharAtPositionX.addCharUsingSubstring(word, letter, 0));
    }

    @Test
    public void whenUsingStringBuilderAndCharacterAddedAtBeginning_shouldAddCharacter() {
        assertEquals("iTitanc", AppendCharAtPositionX.addCharUsingStringBuilder(word, letter, 0));
    }

    @Test
    public void whenUsingCharacterArrayAndCharacterAddedAtMiddle_shouldAddCharacter() {
        assertEquals("Titianc", AppendCharAtPositionX.addCharUsingCharArray(word, letter, 3));
    }

    @Test
    public void whenUsingSubstringAndCharacterAddedAtMiddle_shouldAddCharacter() {
        assertEquals("Titianc", AppendCharAtPositionX.addCharUsingSubstring(word, letter, 3));
    }

    @Test
    public void whenUsingStringBuilderAndCharacterAddedAtMiddle_shouldAddCharacter() {
        assertEquals("Titianc", AppendCharAtPositionX.addCharUsingStringBuilder(word, letter, 3));
    }

    @Test
    public void whenUsingCharacterArrayAndCharacterAddedAtEnd_shouldAddCharacter() {
        assertEquals("Titanci", AppendCharAtPositionX.addCharUsingCharArray(word, letter, word.length()));
    }

    @Test
    public void whenUsingSubstringAndCharacterAddedAtEnd_shouldAddCharacter() {
        assertEquals("Titanci", AppendCharAtPositionX.addCharUsingSubstring(word, letter, word.length()));
    }

    @Test
    public void whenUsingStringBuilderAndCharacterAddedAtEnd_shouldAddCharacter() {
        assertEquals("Titanci", AppendCharAtPositionX.addCharUsingStringBuilder(word, letter, word.length()));
    }

    @Test
    public void whenUsingCharacterArrayAndCharacterAddedAtNegativePosition_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> AppendCharAtPositionX.addCharUsingStringBuilder(word, letter, -1));
    }

    @Test
    public void whenUsingSubstringAndCharacterAddedAtNegativePosition_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> AppendCharAtPositionX.addCharUsingStringBuilder(word, letter, -1));
    }

    @Test
    public void whenUsingStringBuilderAndCharacterAddedAtNegativePosition_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> AppendCharAtPositionX.addCharUsingStringBuilder(word, letter, -1));
    }

    @Test
    public void whenUsingCharacterArrayAndCharacterAddedAtInvalidPosition_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> AppendCharAtPositionX.addCharUsingStringBuilder(word, letter, word.length() + 2));
    }

    @Test
    public void whenUsingSubstringAndCharacterAddedAtInvalidPosition_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> AppendCharAtPositionX.addCharUsingStringBuilder(word, letter, word.length() + 2));
    }

    @Test
    public void whenUsingStringBuilderAndCharacterAddedAtInvalidPosition_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> AppendCharAtPositionX.addCharUsingStringBuilder(word, letter, word.length() + 2));
    }

    @Test
    public void whenUsingCharacterArrayAndCharacterAddedAtPositionXAndStringIsNull_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> AppendCharAtPositionX.addCharUsingStringBuilder(null, letter, 3));
    }

    @Test
    public void whenUsingSubstringAndCharacterAddedAtPositionXAndStringIsNull_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> AppendCharAtPositionX.addCharUsingStringBuilder(null, letter, 3));
    }

    @Test
    public void whenUsingStringBuilderAndCharacterAddedAtPositionXAndStringIsNull_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> AppendCharAtPositionX.addCharUsingStringBuilder(null, letter, 3));
    }
}
