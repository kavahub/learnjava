package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.util.ReverseString;

public class ReverseStringTest {

    private static final String STRING_INPUT = "cat";
    private static final String STRING_INPUT_REVERSED = "tac";
    private static final String SENTENCE = "The quick brown fox jumps over the lazy dog";
    private static final String REVERSED_WORDS_SENTENCE = "dog lazy the over jumps fox brown quick The";
    private static final String REVERSED_STRING_SENTENCE = "god yzal eht revo spmuj xof nworb kciuq ehT";

    @Test
    public void whenReverseIsCalled_ThenCorrectStringIsReturned() {
        String reversed = ReverseString.reverse(STRING_INPUT);
        String reversed_sentenct = ReverseString.reverse(SENTENCE);
        String reversedNull = ReverseString.reverse(null);
        String reversedEmpty = ReverseString.reverse(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertEquals(REVERSED_STRING_SENTENCE, reversed_sentenct);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseUsingStringBuilderIsCalled_ThenCorrectStringIsReturned() throws Exception {
        String reversed = ReverseString.reverseUsingStringBuilder(STRING_INPUT);
        String reversed_sentenct = ReverseString.reverseUsingStringBuilder(SENTENCE);
        String reversedNull = ReverseString.reverseUsingStringBuilder(null);
        String reversedEmpty = ReverseString.reverseUsingStringBuilder(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertEquals(REVERSED_STRING_SENTENCE, reversed_sentenct);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseUsingApacheCommonsIsCalled_ThenCorrectStringIsReturned() throws Exception {
        String reversed = ReverseString.reverseUsingApacheCommons(STRING_INPUT);
        String reversed_sentenct = ReverseString.reverseUsingApacheCommons(SENTENCE);
        String reversedNull = ReverseString.reverseUsingApacheCommons(null);
        String reversedEmpty = ReverseString.reverseUsingApacheCommons(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertEquals(REVERSED_STRING_SENTENCE, reversed_sentenct);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseTheOrderOfWordsIsCalled_ThenCorrectStringIsReturned() {
        String reversed = ReverseString.reverseTheOrderOfWords(SENTENCE);
        String reversedNull = ReverseString.reverseTheOrderOfWords(null);
        String reversedEmpty = ReverseString.reverseTheOrderOfWords(StringUtils.EMPTY);

        assertEquals(REVERSED_WORDS_SENTENCE, reversed);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
        assertEquals(STRING_INPUT, ReverseString.reverseTheOrderOfWords(STRING_INPUT));
    }

    @Test
    public void whenReverseTheOrderOfWordsUsingApacheCommonsIsCalled_ThenCorrectStringIsReturned() {
        String reversed = ReverseString.reverseTheOrderOfWordsUsingApacheCommons(SENTENCE);
        String reversedNull = ReverseString.reverseTheOrderOfWordsUsingApacheCommons(null);
        String reversedEmpty = ReverseString.reverseTheOrderOfWordsUsingApacheCommons(StringUtils.EMPTY);

        assertEquals(REVERSED_WORDS_SENTENCE, reversed);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
        assertEquals(STRING_INPUT, ReverseString.reverseTheOrderOfWordsUsingApacheCommons(STRING_INPUT));
    }
    
}
