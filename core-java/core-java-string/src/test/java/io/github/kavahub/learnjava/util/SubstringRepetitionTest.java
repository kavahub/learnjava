package io.github.kavahub.learnjava.util;

import static io.github.kavahub.learnjava.util.SubstringRepetition.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SubstringRepetitionTest {
    private String validString = "aa";
    private String validStringTwo = "ababab";
    private String validStringThree = "learnjavalearnjava";

    private String invalidString = "aca";
    private String invalidStringTwo = "ababa";
    private String invalidStringThree = "learnjavanonrepeatedlearnjava";
    private String invalidStringFour = "abc abc";

    @Test
    public void givenValidStrings_whenCheckIfContainsOnlySubstrings_thenReturnsTrue() {
        assertTrue(containsOnlySubstrings(validString));
        assertTrue(containsOnlySubstrings(validStringTwo));
        assertTrue(containsOnlySubstrings(validStringThree));
    }

    @Test
    public void givenInvalidStrings_whenCheckIfContainsOnlySubstrings_thenReturnsFalse() {
        assertFalse(containsOnlySubstrings(invalidString));
        assertFalse(containsOnlySubstrings(invalidStringTwo));
        assertFalse(containsOnlySubstrings(invalidStringThree));
        assertFalse(containsOnlySubstrings(invalidStringFour));
    }

    @Test
    public void givenValidStrings_whenCheckEfficientlyIfContainsOnlySubstrings_thenReturnsTrue() {
        assertTrue(containsOnlySubstringsEfficient(validString));
        assertTrue(containsOnlySubstringsEfficient(validStringTwo));
        assertTrue(containsOnlySubstringsEfficient(validStringThree));
    }

    @Test
    public void givenInvalidStrings_whenCheckEfficientlyIfContainsOnlySubstrings_thenReturnsFalse() {
        assertFalse(containsOnlySubstringsEfficient(invalidString));
        assertFalse(containsOnlySubstringsEfficient(invalidStringTwo));
        assertFalse(containsOnlySubstringsEfficient(invalidStringThree));
        assertFalse(containsOnlySubstringsEfficient(invalidStringFour));
    }    
}
