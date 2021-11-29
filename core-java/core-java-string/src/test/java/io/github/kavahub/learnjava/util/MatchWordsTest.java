package io.github.kavahub.learnjava.util;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.MatchWords.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 
 * {@link MatchWords} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MatchWordsTest {
    private final String[] words = {"hello", "Java"};
    private final String inputString = "hello there, Java";
    private final String wholeInput = "helloJava";
    private final String invalidInput = "hello world";

    @Test
    public void givenInvalidText_whenCalling_shouldFalse() {
        assertThat(containsWordsIndexOf(invalidInput, words)).isFalse();
        assertThat(containsWords(invalidInput, words)).isFalse();
        assertThat(containsWordsAhoCorasick(invalidInput, words)).isFalse();
        assertThat(containsWordsPatternMatch(invalidInput, words)).isFalse();
        assertThat(containsWordsJava8(invalidInput, words)).isFalse();
        assertThat(containsWordsArray(invalidInput, words)).isFalse();
    }

    @Test
    public void givenText_whenCallingStringContains_shouldMatchWords() {
        final boolean result = containsWords(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingJava8_shouldMatchWords() {
        final boolean result = containsWordsJava8(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingJava8_shouldNotMatchWords() {
        final boolean result = containsWordsJava8(wholeInput, words);
        assertThat(result).isFalse();
    }

    @Test
    public void givenText_whenCallingPattern_shouldMatchWords() {
        final boolean result = containsWordsPatternMatch(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingAhoCorasick_shouldMatchWords() {
        final boolean result = containsWordsAhoCorasick(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingAhoCorasick_shouldNotMatchWords() {
        final boolean result = containsWordsAhoCorasick(wholeInput, words);
        assertThat(result).isFalse();
    }

    @Test
    public void givenText_whenCallingIndexOf_shouldMatchWords() {
        final boolean result = containsWordsIndexOf(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingArrayList_shouldMatchWords() {
        final boolean result = containsWordsArray(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingArrayList_shouldNotMatchWords() {
        final boolean result = containsWordsArray(wholeInput, words);
        assertThat(result).isFalse();
    }   
}
