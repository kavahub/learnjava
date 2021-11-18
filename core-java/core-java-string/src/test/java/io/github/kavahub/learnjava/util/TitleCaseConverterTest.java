package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.TitleCaseConverter.*;

public class TitleCaseConverterTest {
    private static final String TEXT = "tHis IS a tiTLe";
    private static final String TEXT_EXPECTED = "This Is A Title";
    private static final String TEXT_EXPECTED_NOT_FULL = "THis IS A TiTLe";

    private static final String TEXT_OTHER_DELIMITERS = "tHis, IS a   tiTLe";
    private static final String TEXT_EXPECTED_OTHER_DELIMITERS = "This, Is A   Title";
    private static final String TEXT_EXPECTED_OTHER_DELIMITERS_NOT_FULL = "THis, IS A   TiTLe";

    @Test
    public void whenConvertingToTitleCaseIterating_thenStringConverted() {
        assertEquals(TEXT_EXPECTED, convertToTitleCaseIteratingChars(TEXT));
    }

    @Test
    public void whenConvertingToTitleCaseSplitting_thenStringConverted() {
        assertEquals(TEXT_EXPECTED, convertToTitleCaseSplitting(TEXT));
    }

    @Test
    public void whenConvertingToTitleCaseUsingWordUtilsFull_thenStringConverted() {
        assertEquals(TEXT_EXPECTED, convertToTileCaseWordUtilsFull(TEXT));
    }

    @Test
    public void whenConvertingToTitleCaseUsingWordUtils_thenStringConvertedOnlyFirstCharacter() {
        assertEquals(TEXT_EXPECTED_NOT_FULL, convertToTileCaseWordUtils(TEXT));
    }

    @Test
    public void whenConvertingToTitleCaseUsingIcu4j_thenStringConverted() {
        assertEquals(TEXT_EXPECTED, convertToTitleCaseIcu4j(TEXT));
    }

    @Test
    public void whenConvertingToTitleCaseWithDifferentDelimiters_thenDelimitersKept() {
        assertEquals(TEXT_EXPECTED_OTHER_DELIMITERS, convertToTitleCaseIteratingChars(TEXT_OTHER_DELIMITERS));
        assertEquals(TEXT_EXPECTED_OTHER_DELIMITERS, convertToTitleCaseSplitting(TEXT_OTHER_DELIMITERS));
        assertEquals(TEXT_EXPECTED_OTHER_DELIMITERS, convertToTileCaseWordUtilsFull(TEXT_OTHER_DELIMITERS));
        assertEquals(TEXT_EXPECTED_OTHER_DELIMITERS_NOT_FULL, convertToTileCaseWordUtils(TEXT_OTHER_DELIMITERS));
        assertEquals(TEXT_EXPECTED_OTHER_DELIMITERS, convertToTitleCaseIcu4j(TEXT_OTHER_DELIMITERS));
    }

    @Test
    public void givenNull_whenConvertingToTileCase_thenReturnNull() {
        assertEquals(null, convertToTitleCaseIteratingChars(null));
        assertEquals(null, convertToTitleCaseSplitting(null));
        assertEquals(null, convertToTileCaseWordUtilsFull(null));
        assertEquals(null, convertToTileCaseWordUtils(null));
        assertEquals(null, convertToTitleCaseIcu4j(null));
    }

    @Test
    public void givenEmptyString_whenConvertingToTileCase_thenReturnEmptyString() {
        assertEquals("", convertToTitleCaseIteratingChars(""));
        assertEquals("", convertToTitleCaseSplitting(""));
        assertEquals("", convertToTileCaseWordUtilsFull(""));
        assertEquals("", convertToTileCaseWordUtils(""));
        assertEquals("", convertToTitleCaseIcu4j(""));
    }   
}
