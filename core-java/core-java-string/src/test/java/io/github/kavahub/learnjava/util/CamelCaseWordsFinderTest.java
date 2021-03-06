package io.github.kavahub.learnjava.util;

import static io.github.kavahub.learnjava.util.CamelCaseWordsFinder.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link CamelCaseWordsFinder} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CamelCaseWordsFinderTest {
    @Test
    void givenPlainStringWithNonLetters_thenFindsWords() {
        assertThat(findWordsInMixedCase("some words"))
          .containsExactly("some", "words");
    }

    @Test
    void givenWordsInCamelCase_thenFindsWords() {
        assertThat(findWordsInMixedCase("thisIsCamelCaseText"))
          .containsExactly("this", "Is", "Camel", "Case", "Text");
    }

    @Test
    void givenWordsInTitleCase_thenFindsWords() {
        assertThat(findWordsInMixedCase("ThisIsTitleCaseText"))
          .containsExactly("This", "Is", "Title", "Case", "Text");
    }

    @Test
    void givenWordsAcrossMultipleTexts_thenFindsWords() {
        assertThat(findWordsInMixedCase("ThisIsTitleCaseText --- andSoIsThis"))
          .containsExactly("This", "Is", "Title", "Case", "Text", "and", "So", "Is", "This");
    }

    @Test
    void givenCamelCaseHasASingleLetterWord_thenItCanBeSplit() {
        assertThat(findWordsInMixedCase("thisHasASingleLetterWord"))
          .containsExactly("this", "Has", "A", "Single", "Letter", "Word");
    }
}
