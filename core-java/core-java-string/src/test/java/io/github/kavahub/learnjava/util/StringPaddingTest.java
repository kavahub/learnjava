package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.base.Strings;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.StringPadding.*;

/**
 * 
 * {@link StringPadding} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class StringPaddingTest {
    String inputString = "123456";
    String expectedPaddedStringSpaces = "    123456";
    String expectedPaddedStringZeros = "0000123456";
    int minPaddedStringLength = 10;

    @Test
    public void givenString_whenPaddingWithSpaces_thenStringPaddedMatches() {
        assertEquals(expectedPaddedStringSpaces, padLeftSpaces(inputString, minPaddedStringLength));
    }

    @Test
    public void givenString_whenPaddingWithSpacesUsingSubstring_thenStringPaddedMatches() {
        assertEquals(expectedPaddedStringSpaces, padLeft(inputString, minPaddedStringLength));
    }

    @Test
    public void givenString_whenPaddingWithZeros_thenStringPaddedMatches() {
        assertEquals(expectedPaddedStringZeros, padLeftZeros(inputString, minPaddedStringLength));
    }

    @Test
    public void givenString_whenPaddingWithSpacesUsingStringUtils_thenStringPaddedMatches() {
        assertEquals(expectedPaddedStringSpaces, StringUtils.leftPad(inputString, minPaddedStringLength));
    }

    @Test
    public void givenString_whenPaddingWithZerosUsingStringUtils_thenStringPaddedMatches() {
        assertEquals(expectedPaddedStringZeros, StringUtils.leftPad(inputString, minPaddedStringLength, "0"));
    }

    @Test
    public void givenString_whenPaddingWithSpacesUsingGuavaStrings_thenStringPaddedMatches() {
        assertEquals(expectedPaddedStringSpaces, Strings.padStart(inputString, minPaddedStringLength, ' '));
    }

    @Test
    public void givenString_whenPaddingWithZerosUsingGuavaStrings_thenStringPaddedMatches() {
        assertEquals(expectedPaddedStringZeros, Strings.padStart(inputString, minPaddedStringLength, '0'));
    }   
}
