package io.github.kavahub.learnjava.regex;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.RecapitalizeHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RecapitalizeHelperTest {
    @Test
    void givenWords_thenCanComposeSentence() {
        assertThat(sentenceCase(Arrays.asList("these", "Words", "Form", "A", "Sentence")))
          .isEqualTo("These words form a sentence.");
    }

    @Test
    void givenNonStopWords_thenTitleIsComposed() {
        assertThat(capitalizeMyTitle(Arrays.asList("title", "words", "capitalize")))
          .isEqualTo("Title Words Capitalize");
    }

    @Test
    void givenStopWords_thenTitleHasThemInLowerCase() {
        assertThat(capitalizeMyTitle(Arrays.asList("this", "is", "A", "title", "with", "a", "stop", "word", "or", "two")))
          .isEqualTo("This Is a Title With a Stop Word or Two");
    }

    @Test
    void givenStopWordIsFirstWord_thenTitleHasItCapitalized() {
        assertThat(capitalizeMyTitle(Arrays.asList("a", "stop", "word", "first")))
          .isEqualTo("A Stop Word First");
    }    
}
