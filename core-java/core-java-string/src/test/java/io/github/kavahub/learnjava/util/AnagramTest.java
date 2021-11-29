package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Anagram} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class AnagramTest {
    @Test
    public void givenAnagram_whenUsingSort_thenIdentifyAnagram() {
        String string1 = "abcab";
        String string2 = "cabba";
        assertTrue(Anagram.isAnagramSort(string1, string2));
    }

    @Test
    public void givenAnagram_whenUsingCounting_thenIdentifyAnagram() {
        String string1 = "abcab";
        String string2 = "cabba";
        assertTrue(Anagram.isAnagramCounting(string1, string2));
    }

    @Test
    public void givenAnagram_whenUsingMultiset_thenIdentifyAnagram() {
        String string1 = "abcab";
        String string2 = "cabba";
        assertTrue(Anagram.isAnagramMultiset(string1, string2));
    }

    @Test
    public void givenAnagramNumber_whenUsingMultiset_thenIdentifyAnagram() {
        String string1 = "abc23ab";
        String string2 = "3cabba2";
        assertTrue(Anagram.isAnagramMultiset(string1, string2));
    }


    @Test
    public void givenNonAnagram_whenUsingSort_thenIdentifyNotAnagram() {
        String string1 = "abcaba";
        String string2 = "cabbac";
        assertFalse(Anagram.isAnagramSort(string1, string2));
    }

    @Test
    public void givenNonAnagram_whenUsingCounting_thenIdentifyNotAnagram() {
        String string1 = "abcaba";
        String string2 = "cabbac";
        assertFalse(Anagram.isAnagramCounting(string1, string2));
    }

    @Test
    public void givenNonAnagram_whenUsingMultiset_thenIdentifyNotAnagram() {
        String string1 = "abcaba";
        String string2 = "cabbac";
        assertFalse(Anagram.isAnagramMultiset(string1, string2));
    }

    
    @Test
    public void givenAnagram_whenUsingLetterBasedMultiset_thenIdentifyAnagram() {
        String string1 = "A decimal point";
        String string2 = "I’m a dot in place.";
        assertTrue(Anagram.isLetterBasedAnagramMultiset(string1, string2));
    }

    @Test
    public void ggivenNonAnagram_whenUsingLetterBasedMultiset_thenIdentifyAnagram() {
        String string1 = "A decimal point";
        String string2 = "I’m dot in place.";
        assertFalse(Anagram.isAnagramMultiset(string1, string2));
    }    
}
