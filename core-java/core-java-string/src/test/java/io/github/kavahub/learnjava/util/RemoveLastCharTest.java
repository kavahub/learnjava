package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.StringRemoveLastChar.*;

public class RemoveLastCharTest {
    public static final String TEST_STRING = "abcdef";
    public static final String NULL_STRING = null;
    public static final String EMPTY_STRING = "";
    public static final String ONE_CHAR_STRING = "a";
    public static final String WHITE_SPACE_AT_THE_END_STRING = "abc ";
    public static final String NEW_LINE_AT_THE_END_STRING = "abc\n";
    public static final String MULTIPLE_LINES_STRING = "abc\ndef";

    @Test
    public void givenTestString_whenSubstring_thenGetStingWithoutLastChar() {
        assertEquals("abcde", StringUtils.substring(TEST_STRING, 0, TEST_STRING.length() - 1));
        assertEquals("abcde", StringUtils.chop(TEST_STRING));
        assertEquals("abcde", TEST_STRING.replaceAll(".$", ""));
        assertEquals("abcde", removeLastChar(TEST_STRING));
        assertEquals("abcde", removeLastCharRegex(TEST_STRING));
        assertEquals("abcde", removeLastCharOptional(TEST_STRING));
        assertEquals("abcde", removeLastCharRegexOptional(TEST_STRING));
    }

    @Test
    public void givenNullString_whenSubstring_thenGetNullString() {
        assertEquals(NULL_STRING, removeLastChar(NULL_STRING));
        assertEquals(NULL_STRING, StringUtils.chop(NULL_STRING));
        assertEquals(NULL_STRING, removeLastCharRegex(NULL_STRING));
        assertEquals(NULL_STRING, removeLastCharOptional(NULL_STRING));
        assertEquals(NULL_STRING, removeLastCharRegexOptional(NULL_STRING));
    }

    @Test
    public void givenEmptyString_whenSubstring_thenGetEmptyString() {
        assertEquals(EMPTY_STRING, removeLastChar(EMPTY_STRING));
        assertEquals(EMPTY_STRING, StringUtils.substring(EMPTY_STRING, 0, EMPTY_STRING.length() - 1));
        assertEquals(EMPTY_STRING, StringUtils.chop(EMPTY_STRING));
        assertEquals(EMPTY_STRING, EMPTY_STRING.replaceAll(".$", ""));
        assertEquals(EMPTY_STRING, removeLastCharRegex(EMPTY_STRING));
        assertEquals(EMPTY_STRING, removeLastCharOptional(EMPTY_STRING));
        assertEquals(EMPTY_STRING, removeLastCharRegexOptional(EMPTY_STRING));
    }

    @Test
    public void givenOneCharString_whenSubstring_thenGetEmptyString() {
        assertEquals(EMPTY_STRING, removeLastChar(ONE_CHAR_STRING));
        assertEquals(EMPTY_STRING, StringUtils.substring(ONE_CHAR_STRING, 0, ONE_CHAR_STRING.length() - 1));
        assertEquals(EMPTY_STRING, StringUtils.chop(ONE_CHAR_STRING));
        assertEquals(EMPTY_STRING, ONE_CHAR_STRING.replaceAll(".$", ""));
        assertEquals(EMPTY_STRING, removeLastCharRegex(ONE_CHAR_STRING));
        assertEquals(EMPTY_STRING, removeLastCharOptional(ONE_CHAR_STRING));
        assertEquals(EMPTY_STRING, removeLastCharRegexOptional(ONE_CHAR_STRING));
    }

    @Test
    public void givenStringWithWhiteSpaceAtTheEnd_whenSubstring_thenGetStringWithoutWhiteSpaceAtTheEnd() {
        assertEquals("abc", removeLastChar(WHITE_SPACE_AT_THE_END_STRING));
        assertEquals("abc", StringUtils.substring(WHITE_SPACE_AT_THE_END_STRING, 0, WHITE_SPACE_AT_THE_END_STRING.length() - 1));
        assertEquals("abc", StringUtils.chop(WHITE_SPACE_AT_THE_END_STRING));
        assertEquals("abc", WHITE_SPACE_AT_THE_END_STRING.replaceAll(".$", ""));
        assertEquals("abc", removeLastCharRegex(WHITE_SPACE_AT_THE_END_STRING));
        assertEquals("abc", removeLastCharOptional(WHITE_SPACE_AT_THE_END_STRING));
        assertEquals("abc", removeLastCharRegexOptional(WHITE_SPACE_AT_THE_END_STRING));
    }

    @Test
    public void givenStringWithNewLineAtTheEnd_whenSubstring_thenGetStringWithoutNewLine() {
        assertEquals("abc", removeLastChar(NEW_LINE_AT_THE_END_STRING));
        assertEquals("abc", StringUtils.substring(NEW_LINE_AT_THE_END_STRING, 0, NEW_LINE_AT_THE_END_STRING.length() - 1));
        assertEquals("abc", StringUtils.chop(NEW_LINE_AT_THE_END_STRING));
        assertNotEquals("abc", NEW_LINE_AT_THE_END_STRING.replaceAll(".$", ""));
        assertNotEquals("abc", removeLastCharRegex(NEW_LINE_AT_THE_END_STRING));
        assertEquals("abc", removeLastCharOptional(NEW_LINE_AT_THE_END_STRING));
        assertNotEquals("abc", removeLastCharRegexOptional(NEW_LINE_AT_THE_END_STRING));
    }

    @Test
    public void givenMultiLineString_whenSubstring_thenGetStringWithoutNewLine() {
        assertEquals("abc\nde", removeLastChar(MULTIPLE_LINES_STRING));
        assertEquals("abc\nde", StringUtils.substring(MULTIPLE_LINES_STRING, 0, MULTIPLE_LINES_STRING.length() - 1));
        assertEquals("abc\nde", StringUtils.chop(MULTIPLE_LINES_STRING));
        assertEquals("abc\nde", MULTIPLE_LINES_STRING.replaceAll(".$", ""));
        assertEquals("abc\nde", removeLastCharRegex(MULTIPLE_LINES_STRING));
        assertEquals("abc\nde", removeLastCharOptional(MULTIPLE_LINES_STRING));
        assertEquals("abc\nde", removeLastCharRegexOptional(MULTIPLE_LINES_STRING));
    }    
}
