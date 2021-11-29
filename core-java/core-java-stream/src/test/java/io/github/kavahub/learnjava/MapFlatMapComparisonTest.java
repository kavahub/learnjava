package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * 
 * map 与 flatMap 的区别
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MapFlatMapComparisonTest {
    @Test
    public void givenStream_whenCalledMap_thenProduceList() {
        List<String> myList = Stream.of("a", "b").map(String::toUpperCase).collect(Collectors.toList());
        assertEquals(Arrays.asList("A", "B"), myList);
    }

    @Test
    public void givenStream_whenCalledFlatMap_thenProduceFlattenedList() throws Exception {
        List<List<String>> list = Arrays.asList(Arrays.asList("a", "b"), Arrays.asList("c", "d"));

        assertEquals(Arrays.asList("a", "b", "c", "d"),
                list.stream().flatMap(Collection::stream).collect(Collectors.toList()));
    }

    @Test
    public void givenOptional_whenCalledMap_thenProduceOptional() {
        Optional<String> s = Optional.of("test");
        assertEquals(Optional.of("TEST"), s.map(String::toUpperCase));
    }

    @Test
    public void givenOptional_whenCalledFlatMap_thenProduceFlattenedOptional() {
        Optional<String> s = Optional.of("test");
        assertEquals(Optional.of("TEST"), s.flatMap(e -> Optional.of(e.toUpperCase())));
    }
}
