package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.enumeration.EnumerationStream;

public class EnumerationStreamTest {
    @Test
    public void givenEnumeration_whenConvertedToStream_thenNotNull() {
        Vector<Integer> input = new Vector<>(Arrays.asList(2, 3, 1, 5, 4));

        Stream<Integer> resultingStream = EnumerationStream.of(input.elements());

        assertNotNull(resultingStream);
    }

    @Test
    public void whenConvertedToList_thenCorrect() {
        Vector<Integer> input = new Vector<>(Arrays.asList(1, 2, 3, 4, 5));

        Stream<Integer> stream = EnumerationStream.of(input.elements());
        List<Integer> list = stream.filter(e -> e >= 3)
            .collect(Collectors.toList());
        assertThat(list, contains(3, 4, 5));
    } 
}
