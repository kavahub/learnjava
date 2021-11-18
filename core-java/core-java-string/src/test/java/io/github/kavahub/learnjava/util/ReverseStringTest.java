package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.ReverseString.*;

public class ReverseStringTest {

    private static final String STRING_INPUT = "cat";
    private static final String STRING_INPUT_REVERSED = "tac";
    private static final String SENTENCE = "The quick brown fox jumps over the lazy dog";
    private static final String REVERSED_WORDS_SENTENCE = "dog lazy the over jumps fox brown quick The";
    private static final String REVERSED_STRING_SENTENCE = "god yzal eht revo spmuj xof nworb kciuq ehT";

    @Test
    public void whenReverseIsCalled_ThenCorrectStringIsReturned() {
        String reversed = reverse(STRING_INPUT);
        String reversed_sentenct = reverse(SENTENCE);
        String reversedNull = reverse(null);
        String reversedEmpty = reverse(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertEquals(REVERSED_STRING_SENTENCE, reversed_sentenct);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseUsingStringBuilderIsCalled_ThenCorrectStringIsReturned() throws Exception {
        String reversed = reverseUsingStringBuilder(STRING_INPUT);
        String reversed_sentenct = reverseUsingStringBuilder(SENTENCE);
        String reversedNull = reverseUsingStringBuilder(null);
        String reversedEmpty = reverseUsingStringBuilder(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertEquals(REVERSED_STRING_SENTENCE, reversed_sentenct);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseUsingApacheCommonsIsCalled_ThenCorrectStringIsReturned() throws Exception {
        String reversed = reverseUsingApacheCommons(STRING_INPUT);
        String reversed_sentenct = reverseUsingApacheCommons(SENTENCE);
        String reversedNull = reverseUsingApacheCommons(null);
        String reversedEmpty = reverseUsingApacheCommons(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertEquals(REVERSED_STRING_SENTENCE, reversed_sentenct);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseTheOrderOfWordsIsCalled_ThenCorrectStringIsReturned() {
        String reversed = reverseTheOrderOfWords(SENTENCE);
        String reversedNull = reverseTheOrderOfWords(null);
        String reversedEmpty = reverseTheOrderOfWords(StringUtils.EMPTY);

        assertEquals(REVERSED_WORDS_SENTENCE, reversed);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
        assertEquals(STRING_INPUT, reverseTheOrderOfWords(STRING_INPUT));
    }

    @Test
    public void whenReverseTheOrderOfWordsUsingApacheCommonsIsCalled_ThenCorrectStringIsReturned() {
        String reversed = reverseTheOrderOfWordsUsingApacheCommons(SENTENCE);
        String reversedNull = reverseTheOrderOfWordsUsingApacheCommons(null);
        String reversedEmpty = reverseTheOrderOfWordsUsingApacheCommons(StringUtils.EMPTY);

        assertEquals(REVERSED_WORDS_SENTENCE, reversed);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
        assertEquals(STRING_INPUT, reverseTheOrderOfWordsUsingApacheCommons(STRING_INPUT));
    }
    
}
