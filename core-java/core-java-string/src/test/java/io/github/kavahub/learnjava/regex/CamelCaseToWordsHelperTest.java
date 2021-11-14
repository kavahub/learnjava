package io.github.kavahub.learnjava.regex;

import static io.github.kavahub.learnjava.regex.CamelCaseToWordsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CamelCaseToWordsHelperTest {
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
