package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.util.StringRemoveHelper;

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
        assertEquals("abcde", StringRemoveHelper.removeLastChar(TEST_STRING));
        assertEquals("abcde", StringRemoveHelper.removeLastCharRegex(TEST_STRING));
        assertEquals("abcde", StringRemoveHelper.removeLastCharOptional(TEST_STRING));
        assertEquals("abcde", StringRemoveHelper.removeLastCharRegexOptional(TEST_STRING));
    }

    @Test
    public void givenNullString_whenSubstring_thenGetNullString() {
        assertEquals(NULL_STRING, StringRemoveHelper.removeLastChar(NULL_STRING));
        assertEquals(NULL_STRING, StringUtils.chop(NULL_STRING));
        assertEquals(NULL_STRING, StringRemoveHelper.removeLastCharRegex(NULL_STRING));
        assertEquals(NULL_STRING, StringRemoveHelper.removeLastCharOptional(NULL_STRING));
        assertEquals(NULL_STRING, StringRemoveHelper.removeLastCharRegexOptional(NULL_STRING));
    }

    @Test
    public void givenEmptyString_whenSubstring_thenGetEmptyString() {
        assertEquals(EMPTY_STRING, StringRemoveHelper.removeLastChar(EMPTY_STRING));
        assertEquals(EMPTY_STRING, StringUtils.substring(EMPTY_STRING, 0, EMPTY_STRING.length() - 1));
        assertEquals(EMPTY_STRING, StringUtils.chop(EMPTY_STRING));
        assertEquals(EMPTY_STRING, EMPTY_STRING.replaceAll(".$", ""));
        assertEquals(EMPTY_STRING, StringRemoveHelper.removeLastCharRegex(EMPTY_STRING));
        assertEquals(EMPTY_STRING, StringRemoveHelper.removeLastCharOptional(EMPTY_STRING));
        assertEquals(EMPTY_STRING, StringRemoveHelper.removeLastCharRegexOptional(EMPTY_STRING));
    }

    @Test
    public void givenOneCharString_whenSubstring_thenGetEmptyString() {
        assertEquals(EMPTY_STRING, StringRemoveHelper.removeLastChar(ONE_CHAR_STRING));
        assertEquals(EMPTY_STRING, StringUtils.substring(ONE_CHAR_STRING, 0, ONE_CHAR_STRING.length() - 1));
        assertEquals(EMPTY_STRING, StringUtils.chop(ONE_CHAR_STRING));
        assertEquals(EMPTY_STRING, ONE_CHAR_STRING.replaceAll(".$", ""));
        assertEquals(EMPTY_STRING, StringRemoveHelper.removeLastCharRegex(ONE_CHAR_STRING));
        assertEquals(EMPTY_STRING, StringRemoveHelper.removeLastCharOptional(ONE_CHAR_STRING));
        assertEquals(EMPTY_STRING, StringRemoveHelper.removeLastCharRegexOptional(ONE_CHAR_STRING));
    }

    @Test
    public void givenStringWithWhiteSpaceAtTheEnd_whenSubstring_thenGetStringWithoutWhiteSpaceAtTheEnd() {
        assertEquals("abc", StringRemoveHelper.removeLastChar(WHITE_SPACE_AT_THE_END_STRING));
        assertEquals("abc", StringUtils.substring(WHITE_SPACE_AT_THE_END_STRING, 0, WHITE_SPACE_AT_THE_END_STRING.length() - 1));
        assertEquals("abc", StringUtils.chop(WHITE_SPACE_AT_THE_END_STRING));
        assertEquals("abc", WHITE_SPACE_AT_THE_END_STRING.replaceAll(".$", ""));
        assertEquals("abc", StringRemoveHelper.removeLastCharRegex(WHITE_SPACE_AT_THE_END_STRING));
        assertEquals("abc", StringRemoveHelper.removeLastCharOptional(WHITE_SPACE_AT_THE_END_STRING));
        assertEquals("abc", StringRemoveHelper.removeLastCharRegexOptional(WHITE_SPACE_AT_THE_END_STRING));
    }

    @Test
    public void givenStringWithNewLineAtTheEnd_whenSubstring_thenGetStringWithoutNewLine() {
        assertEquals("abc", StringRemoveHelper.removeLastChar(NEW_LINE_AT_THE_END_STRING));
        assertEquals("abc", StringUtils.substring(NEW_LINE_AT_THE_END_STRING, 0, NEW_LINE_AT_THE_END_STRING.length() - 1));
        assertEquals("abc", StringUtils.chop(NEW_LINE_AT_THE_END_STRING));
        assertNotEquals("abc", NEW_LINE_AT_THE_END_STRING.replaceAll(".$", ""));
        assertNotEquals("abc", StringRemoveHelper.removeLastCharRegex(NEW_LINE_AT_THE_END_STRING));
        assertEquals("abc", StringRemoveHelper.removeLastCharOptional(NEW_LINE_AT_THE_END_STRING));
        assertNotEquals("abc", StringRemoveHelper.removeLastCharRegexOptional(NEW_LINE_AT_THE_END_STRING));
    }

    @Test
    public void givenMultiLineString_whenSubstring_thenGetStringWithoutNewLine() {
        assertEquals("abc\nde", StringRemoveHelper.removeLastChar(MULTIPLE_LINES_STRING));
        assertEquals("abc\nde", StringUtils.substring(MULTIPLE_LINES_STRING, 0, MULTIPLE_LINES_STRING.length() - 1));
        assertEquals("abc\nde", StringUtils.chop(MULTIPLE_LINES_STRING));
        assertEquals("abc\nde", MULTIPLE_LINES_STRING.replaceAll(".$", ""));
        assertEquals("abc\nde", StringRemoveHelper.removeLastCharRegex(MULTIPLE_LINES_STRING));
        assertEquals("abc\nde", StringRemoveHelper.removeLastCharOptional(MULTIPLE_LINES_STRING));
        assertEquals("abc\nde", StringRemoveHelper.removeLastCharRegexOptional(MULTIPLE_LINES_STRING));
    }    
}
