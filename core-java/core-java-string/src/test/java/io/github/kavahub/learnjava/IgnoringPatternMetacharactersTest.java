package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * 
 * 正则表达式 使用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class IgnoringPatternMetacharactersTest {
    private static final String dollarAmounts = "$100.25, $100.50, $150.50, $100.50, $100.75";
    private static final String patternStr = "$100.50";

    @Test
    public void whenMetacharactersNotEscaped_thenNoMatchesFound() {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(dollarAmounts);

        int matches = 0;
        while (matcher.find()) {
            matches++;
        }

        assertEquals(0, matches);
    }

    @Test
    public void whenMetacharactersManuallyEscaped_thenMatchingSuccessful() {
        String metaEscapedPatternStr = "\\Q" + patternStr + "\\E";
        Pattern pattern = Pattern.compile(metaEscapedPatternStr);
        Matcher matcher = pattern.matcher(dollarAmounts);

        int matches = 0;
        while (matcher.find()) {
            matches++;
        }

        assertEquals(2, matches);
    }

    @Test
    public void whenMetacharactersEscapedUsingPatternQuote_thenMatchingSuccessful() {
        String literalPatternStr = Pattern.quote(patternStr);
        Pattern pattern = Pattern.compile(literalPatternStr);
        Matcher matcher = pattern.matcher(dollarAmounts);

        int matches = 0;
        while (matcher.find()) {
            matches++;
        }

        assertEquals(2, matches);
    }    
}
