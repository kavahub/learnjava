package io.github.kavahub.learnjava.combine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class CombiningSetsTest {
    private static final Set<String> first = new HashSet<String>(Arrays.asList(new String[] { "One", "Two", "Three" }));

	private static final Set<String> second = new HashSet<String>(Arrays.asList(new String[] { "Four", "Five", "Six" }));

	private static final Set<String> expected = new HashSet<String>(Arrays
			.asList(new String[] { "One", "Two", "Three", "Four", "Five", "Six" }));

	@Test
	public void givenTwoSets_whenUsingNativeJava_thenArraysCombined() {
		assertThat(CombiningSets.usingNativeJava(first, second), is(expected));
	}

	@Test
	public void givenTwoSets_whenUsingObjectStreams_thenArraysCombined() {
		assertThat(CombiningSets.usingJava8ObjectStream(first, second), is(expected));
	}

	@Test
	public void givenTwoSets_whenUsingFlatMaps_thenArraysCombined() {
		assertThat(CombiningSets.usingJava8FlatMaps(first, second), is(expected));
	}

	@Test
	public void givenTwoSets_whenUsingApacheCommons_thenArraysCombined() {
		assertThat(CombiningSets.usingApacheCommons(first, second), is(expected));
	}

	@Test
	public void givenTwoSets_whenUsingGuava_thenArraysCombined() {
		assertThat(CombiningSets.usingGuava(first, second), is(expected));
	}
}
