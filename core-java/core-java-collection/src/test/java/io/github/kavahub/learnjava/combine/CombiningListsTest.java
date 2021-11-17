package io.github.kavahub.learnjava.combine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CombiningListsTest {
    private static final List<String> first = Arrays.asList(new String[] { "One", "Two", "Three" });

    private static final List<String> second = Arrays.asList(new String[] { "Four", "Five", "Six" });

    private static final List<String> expected = Arrays
            .asList(new String[] { "One", "Two", "Three", "Four", "Five", "Six" });

    @Test
    public void givenTwoLists_whenUsingNativeJava_thenArraysCombined() {
        assertThat(CombiningLists.usingNativeJava(first, second), is(expected));
    }

    @Test
    public void givenTwoLists_whenUsingObjectStreams_thenArraysCombined() {
        assertThat(CombiningLists.usingJava8ObjectStream(first, second), is(expected));
    }

    @Test
    public void givenTwoLists_whenUsingFlatMaps_thenArraysCombined() {
        assertThat(CombiningLists.usingJava8FlatMaps(first, second), is(expected));
    }

    @Test
    public void givenTwoLists_whenUsingApacheCommons_thenArraysCombined() {
        assertThat(CombiningLists.usingApacheCommons(first, second), is(expected));
    }

    @Test
    public void givenTwoLists_whenUsingGuava_thenArraysCombined() {
        assertThat(CombiningLists.usingGuava(first, second), is(expected));
    }
}
