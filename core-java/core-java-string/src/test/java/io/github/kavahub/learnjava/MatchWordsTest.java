package io.github.kavahub.learnjava;

import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.util.MatchWords;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchWordsTest {
    private final String[] words = {"hello", "Java"};
    private final String inputString = "hello there, Java";
    private final String wholeInput = "helloJava";
    private final String invalidInput = "hello world";

    @Test
    public void givenInvalidText_whenCalling_shouldFalse() {
        assertThat(MatchWords.containsWordsIndexOf(invalidInput, words)).isFalse();
        assertThat(MatchWords.containsWords(invalidInput, words)).isFalse();
        assertThat(MatchWords.containsWordsAhoCorasick(invalidInput, words)).isFalse();
        assertThat(MatchWords.containsWordsPatternMatch(invalidInput, words)).isFalse();
        assertThat(MatchWords.containsWordsJava8(invalidInput, words)).isFalse();
        assertThat(MatchWords.containsWordsArray(invalidInput, words)).isFalse();
    }

    @Test
    public void givenText_whenCallingStringContains_shouldMatchWords() {
        final boolean result = MatchWords.containsWords(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingJava8_shouldMatchWords() {
        final boolean result = MatchWords.containsWordsJava8(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingJava8_shouldNotMatchWords() {
        final boolean result = MatchWords.containsWordsJava8(wholeInput, words);
        assertThat(result).isFalse();
    }

    @Test
    public void givenText_whenCallingPattern_shouldMatchWords() {
        final boolean result = MatchWords.containsWordsPatternMatch(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingAhoCorasick_shouldMatchWords() {
        final boolean result = MatchWords.containsWordsAhoCorasick(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingAhoCorasick_shouldNotMatchWords() {
        final boolean result = MatchWords.containsWordsAhoCorasick(wholeInput, words);
        assertThat(result).isFalse();
    }

    @Test
    public void givenText_whenCallingIndexOf_shouldMatchWords() {
        final boolean result = MatchWords.containsWordsIndexOf(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingArrayList_shouldMatchWords() {
        final boolean result = MatchWords.containsWordsArray(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingArrayList_shouldNotMatchWords() {
        final boolean result = MatchWords.containsWordsArray(wholeInput, words);
        assertThat(result).isFalse();
    }   
}
