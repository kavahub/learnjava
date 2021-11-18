package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.util.MultiLineString;

public class MultiLineStringTest {
    @Test
    public void whenCompareMultiLineStrings_thenTheyAreAllTheSame() throws IOException {
        MultiLineString ms = new MultiLineString();
        assertEquals(ms.stringConcatenation(), ms.stringJoin());
        assertEquals(ms.stringJoin(), ms.stringBuilder());
        assertEquals(ms.stringBuilder(), ms.guavaJoiner());
        assertEquals(ms.guavaJoiner(), ms.loadFromFile());
        //assertEquals(ms.loadFromFile(), ms.textBlocks());
    }    
}
