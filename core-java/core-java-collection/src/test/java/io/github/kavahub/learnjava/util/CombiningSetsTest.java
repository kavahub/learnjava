package io.github.kavahub.learnjava.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.CombiningSets.*;

public class CombiningSetsTest {
    private static final Set<String> first = new HashSet<String>(Arrays.asList(new String[] { "One", "Two", "Three" }));

	private static final Set<String> second = new HashSet<String>(Arrays.asList(new String[] { "Four", "Five", "Six" }));

	private static final Set<String> expected = new HashSet<String>(Arrays
			.asList(new String[] { "One", "Two", "Three", "Four", "Five", "Six" }));

	@Test
	public void givenTwoSets_whenUsingNativeJava_thenArraysCombined() {
		assertThat(usingNativeJava(first, second), is(expected));
	}

	@Test
	public void givenTwoSets_whenUsingObjectStreams_thenArraysCombined() {
		assertThat(usingJava8ObjectStream(first, second), is(expected));
	}

	@Test
	public void givenTwoSets_whenUsingFlatMaps_thenArraysCombined() {
		assertThat(usingJava8FlatMaps(first, second), is(expected));
	}

	@Test
	public void givenTwoSets_whenUsingApacheCommons_thenArraysCombined() {
		assertThat(usingApacheCommons(first, second), is(expected));
	}

	@Test
	public void givenTwoSets_whenUsingGuava_thenArraysCombined() {
		assertThat(usingGuava(first, second), is(expected));
	}
}
