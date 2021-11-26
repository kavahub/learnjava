package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.CombiningArrays.*;

public class CombiningArraysTest {
    private static final String first[] = { "One", "Two", "Three" };

    private static final String second[] = { "Four", "Five", "Six" };

    private static final String expected[] = { "One", "Two", "Three", "Four", "Five", "Six" };

    @Test
    public void givenTwoArrays_whenUsingNativeJava_thenArraysCombined() {
        assertArrayEquals(expected, usingNativeJava(first, second));
    }

    @Test
    public void givenTwoArrays_whenUsingObjectStreams_thenArraysCombined() {
        assertArrayEquals(expected, usingJava8ObjectStream(first, second));
    }

    @Test
    public void givenTwoArrays_whenUsingFlatMaps_thenArraysCombined() {
        assertArrayEquals(expected, usingJava8FlatMaps(first, second));
    }

    @Test
    public void givenTwoArrays_whenUsingApacheCommons_thenArraysCombined() {
        assertArrayEquals(expected, usingApacheCommons(first, second));
    }

    @Test
    public void givenTwoArrays_whenUsingGuava_thenArraysCombined() {
        assertArrayEquals(expected, usingGuava(first, second));
    }
}
