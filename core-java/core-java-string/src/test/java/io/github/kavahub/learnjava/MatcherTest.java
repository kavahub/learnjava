package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class MatcherTest {
    @Test
    public void whenFindFourDigitWorks_thenCorrect() {
        Pattern stringPattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher m = stringPattern.matcher("goodbye 2019 and welcome 2020");

        assertTrue(m.find());
        assertEquals(8, m.start());
        assertEquals("2019", m.group());
        assertEquals(12, m.end());

        assertTrue(m.find());
        assertEquals(25, m.start());
        assertEquals("2020", m.group());
        assertEquals(29, m.end());

        assertFalse(m.find());
    }

    @Test
    public void givenStartIndex_whenFindFourDigitWorks_thenCorrect() {
        Pattern stringPattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher m = stringPattern.matcher("goodbye 2019 and welcome 2020");

        assertTrue(m.find(20));
        assertEquals(25, m.start());
        assertEquals("2020", m.group());
        assertEquals(29, m.end());
    }

    @Test
    public void whenMatchFourDigitWorks_thenFail() {
        Pattern stringPattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher m = stringPattern.matcher("goodbye 2019 and welcome 2020");
        // 全匹配
        assertFalse(m.matches());
    }

    @Test
    public void whenMatchFourDigitWorks_thenCorrect() {
        Pattern stringPattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher m = stringPattern.matcher("2019");

        assertTrue(m.matches());
        assertEquals(0, m.start());
        assertEquals("2019", m.group());
        assertEquals(4, m.end());

        assertTrue(m.matches());// matches will always return the same return
    }    
}
