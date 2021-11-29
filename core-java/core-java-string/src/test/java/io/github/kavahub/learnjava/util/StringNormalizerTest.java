package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.text.Normalizer;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.StringNormalizer.*;

/**
 * 
 * {@link StringNormalizer} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class StringNormalizerTest {
    @Test
    public void givenNotNormalizedString_whenIsNormalized_thenReturnFalse() {
        assertFalse(Normalizer.isNormalized("āăąēîïĩíĝġńñšŝśûůŷ", Normalizer.Form.NFKD));
    }

    @Test
    void givenStringWithDecomposableUnicodeCharacters_whenRemoveAccents_thenReturnASCIIString() {
        assertEquals("aaaeiiiiggnnsssuuy", removeAccents("āăąēîïĩíĝġńñšŝśûůŷ"));
    }

    @Test
    void givenStringWithDecomposableUnicodeCharacters_whenRemoveAccentsWithApacheCommons_thenReturnASCIIString() {
        assertEquals("aaaeiiiiggnnsssuuy", removeAccentsWithApacheCommons("āăąēîïĩíĝġńñšŝśûůŷ"));
    }

    @Test
    void givenStringWithNondecomposableUnicodeCharacters_whenRemoveAccents_thenReturnOriginalString() {
        assertEquals("łđħœ", removeAccents("łđħœ"));
    }

    @Test
    void givenStringWithNondecomposableUnicodeCharacters_whenRemoveAccentsWithApacheCommons_thenReturnModifiedString() {
        assertEquals("lđħœ", removeAccentsWithApacheCommons("łđħœ"));
    }

    @Test
    void givenStringWithDecomposableUnicodeCharacters_whenUnicodeValueOfNormalizedString_thenReturnUnicodeValue() {
        assertEquals("\\u0066 \\u0069", unicodeValueOfNormalizedString("ﬁ"));
        assertEquals("\\u0061 \\u0304", unicodeValueOfNormalizedString("ā"));
        assertEquals("\\u0069 \\u0308", unicodeValueOfNormalizedString("ï"));
        assertEquals("\\u006e \\u0301", unicodeValueOfNormalizedString("ń"));
    }

    @Test
    void givenStringWithNonDecomposableUnicodeCharacters_whenUnicodeValueOfNormalizedString_thenReturnOriginalValue() {
        assertEquals("\\u0142", unicodeValueOfNormalizedString("ł"));
        assertEquals("\\u0127", unicodeValueOfNormalizedString("ħ"));
        assertEquals("\\u0111", unicodeValueOfNormalizedString("đ"));
    }    
}
