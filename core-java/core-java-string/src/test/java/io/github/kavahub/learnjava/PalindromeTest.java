package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PalindromeTest {
    private String[] words = { "Anna", "Civic", "Kayak", "Level", "Madam", };

    private String[] sentences = { "Sore was I ere I saw Eros", "Euston saw I was not Sue", "Too hot to hoot",
            "No mists or frost Simon", "Stella won no wallets" };

    @Test
    public void whenWord_shouldBePalindrome() {
        for (String word : words)
            assertTrue(Palindrome.isPalindrome(word));
    }

    @Test
    public void whenSentence_shouldBePalindrome() {
        for (String sentence : sentences)
            assertTrue(Palindrome.isPalindrome(sentence));
    }

    @Test
    public void whenReverseWord_shouldBePalindrome() {
        for (String word : words)
            assertTrue(Palindrome.isPalindromeReverseTheString(word));
    }

    @Test
    public void whenReverseSentence_shouldBePalindrome() {
        for (String sentence : sentences)
            assertTrue(Palindrome.isPalindromeReverseTheString(sentence));
    }

    @Test
    public void whenStringBuilderWord_shouldBePalindrome() {
        for (String word : words)
            assertTrue(Palindrome.isPalindromeUsingStringBuilder(word));
    }

    @Test
    public void whenStringBuilderSentence_shouldBePalindrome() {
        for (String sentence : sentences)
            assertTrue(Palindrome.isPalindromeUsingStringBuilder(sentence));
    }

    @Test
    public void whenStringBufferWord_shouldBePalindrome() {
        for (String word : words)
            assertTrue(Palindrome.isPalindromeUsingStringBuffer(word));
    }

    @Test
    public void whenStringBufferSentence_shouldBePalindrome() {
        for (String sentence : sentences)
            assertTrue(Palindrome.isPalindromeUsingStringBuffer(sentence));
    }

    @Test
    public void whenPalindromeRecursive_wordShouldBePalindrome() {
        for (String word : words)
            assertTrue(Palindrome.isPalindromeRecursive(word));
    }

    @Test
    public void whenPalindromeRecursive_sentenceShouldBePalindrome() {
        for (String sentence : sentences)
            assertTrue(Palindrome.isPalindromeRecursive(sentence));
    }

    @Test
    public void whenPalindromeStreams_wordShouldBePalindrome() {
        for (String word : words)
            assertTrue(Palindrome.isPalindromeUsingIntStream(word));
    }

    @Test
    public void whenPalindromeStreams_sentenceShouldBePalindrome() {
        for (String sentence : sentences)
            assertTrue(Palindrome.isPalindromeUsingIntStream(sentence));
    }
}
