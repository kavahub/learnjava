package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.jupiter.api.Test;

public class IterableStreamTest {
    @Test
    public void givenIterable_whenConvertedToStream_thenNotNull() {
        Iterable<String> iterable = Arrays.asList("Testing", "Iterable", "conversion", "to", "Stream");

        assertNotNull(StreamSupport.stream(iterable.spliterator(), false));
    }

    @Test
    public void whenConvertedToList_thenCorrect() {
        Iterable<String> iterable = Arrays.asList("Testing", "Iterable", "conversion", "to", "Stream");

        // 将Iterable转换成流
        List<String> result = StreamSupport.stream(iterable.spliterator(), false).map(String::toUpperCase)
                .collect(Collectors.toList());

        assertThat(result, contains("TESTING", "ITERABLE", "CONVERSION", "TO", "STREAM"));
    }
}
