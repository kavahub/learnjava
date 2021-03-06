package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.WordFinder.*;

/**
 * 
 * {@link WordFinder} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class WordFinderTest {
    final String theString = "To be, or not to be: that is the question: "
            + "Whether 'tis nobler in the mind to suffer " + "The slings and arrows of outrageous fortune, "
            + "Or to take arms against a sea of troubles, " + "And by opposing end them? To die: to sleep; "
            + "No more; and by a sleep to say we end " + "The heart-ache and the thousand natural shocks "
            + "That flesh is heir to, 'tis a consummation " + "Devoutly to be wish'd. To die, to sleep; "
            + "To sleep: perchance to dream: ay, there's the rub: "
            + "For in that sleep of death what dreams may come,";

    @Test
    public void givenWord_whenSearching_thenFindAllIndexedLocations() {
        List<Integer> expectedResult = Arrays.asList(7, 122, 130, 221, 438);

        List<Integer> actualResult = findWord(theString, "or");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenWordWithNoRepeatCharacters_whenImprovedSearching_thenFindAllIndexedLocations() {
        List<Integer> expectedResult = Arrays.asList(7, 122, 130, 221, 438);

        List<Integer> actualResult = findWordUpgrade(theString, "or");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenWord_whenSearching_thenFindAtEndOfString() {
        List<Integer> expectedResult = Arrays.asList(480);

        List<Integer> actualResult = findWordUpgrade(theString, "come,");

        assertEquals(expectedResult, actualResult);
    }
}
