package io.github.kavahub.learnjava.regex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ReplaceAllWithWhitespaceRegexTest {
    private static final String INPUT_STR = "Text   With     Whitespaces!   ";

    @Test
    public void givenString_whenReplaceBySingleCharClass_thenGetExpect() {
        String expected = "Text___With_____Whitespaces!___";
        String result = INPUT_STR.replaceAll("\\s", "_");
        assertEquals(expected, result);
    }

    @Test
    public void givenString_whenReplaceBySingleCharClassWithPlus_thenGetExpect() {
        String expected = "Text_With_Whitespaces!_";
        String result = INPUT_STR.replaceAll("\\s+", "_");
        assertEquals(expected, result);
    }

    @Test
    public void givenString_whenRemoveByWhitespace_thenGetSameResult() {
        String expected = "TextWithWhitespaces!";
        String result1 = INPUT_STR.replaceAll("\\s", "");
        String result2 = INPUT_STR.replaceAll("\\s+", "");
        assertEquals(expected, result1);
        assertEquals(result1, result2);
    }   
}
