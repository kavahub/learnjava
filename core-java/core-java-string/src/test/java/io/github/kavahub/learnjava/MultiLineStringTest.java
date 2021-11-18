package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.MultiLineStringJoiner.*;

public class MultiLineStringTest {
    @Test
    public void whenCompareMultiLineStrings_thenTheyAreAllTheSame() throws IOException {
        assertEquals(stringConcatenation(), stringJoin());
        assertEquals(stringJoin(), stringBuilder());
        assertEquals(stringBuilder(), guavaJoiner());
        assertEquals(guavaJoiner(), loadFromFile());
        //assertEquals(loadFromFile(), textBlocks());
    }    
}
