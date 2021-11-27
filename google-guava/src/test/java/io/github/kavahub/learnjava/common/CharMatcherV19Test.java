package io.github.kavahub.learnjava.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.base.CharMatcher;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link CharMatcher} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CharMatcherV19Test {
    @Test
    public void whenMatchingLetterOrString_ShouldReturnTrueForCorrectString() throws Exception {
        String inputString = "someString789";
        boolean result = CharMatcher.inRange('0', 'z').matchesAllOf(inputString);

        assertTrue(result);
    }

    @Test
    public void whenCollapsingString_ShouldReturnStringWithDashesInsteadOfWhitespaces() throws Exception {
        String inputPhoneNumber = "8 123 456 123";
        // 替换功能
        String result = CharMatcher.whitespace().collapseFrom(inputPhoneNumber, '-');

        assertEquals("8-123-456-123", result);
    }

    @Test
    public void whenCountingDigitsInString_ShouldReturnActualCountOfDigits() throws Exception {
        String inputPhoneNumber = "8 123 456 123";
        // 统计功能
        int result = CharMatcher.inRange('0', '9').countIn(inputPhoneNumber);

        assertEquals(10, result);
    }   
}
