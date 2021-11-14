package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static java.util.function.Predicate.not;
import org.junit.jupiter.api.Test;

public class NewStringAPITest {
    @Test
    public void whenRepeatStringTwice_thenGetStringTwice() {
        String output = "La ".repeat(2) + "Land";

        assertEquals(output, "La La Land");
    }

    @Test
    public void whenStripString_thenReturnStringWithoutWhitespaces() {
        assertEquals("\n\t  hello   \u2005".strip(), "hello");
    }

    @Test
    public void whenTrimAdvanceString_thenReturnStringWithWhitespaces() {
        assertEquals("\n\t  hello   \u2005".trim(), "hello   \u2005");
    }

    @Test
    public void whenBlankString_thenReturnTrue() {
        assertTrue("\n\t\u2005  ".isBlank());
    }

    @Test
    public void whenMultilineString_thenReturnNonEmptyLineCount() {
        String multilineStr = "This is\n \n a multiline\n string.";

        long lineCount = multilineStr.lines()
          .filter(not(String::isBlank))
          .count();

        assertTrue(lineCount == 3L);
    }    
}
