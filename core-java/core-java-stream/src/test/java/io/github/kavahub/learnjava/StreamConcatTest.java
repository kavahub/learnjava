package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class StreamConcatTest {
    @Test
    public void givenStream_whenAppendingObject_thenAppended() {
        Stream<String> anStream = Stream.of("a", "b", "c", "d", "e");

        Stream<String> newStream = Stream.concat(anStream, Stream.of("A"));

        List<String> resultList = newStream.collect(Collectors.toList());
        assertEquals(resultList.get(resultList.size() - 1), "A");
    }

    @Test
    public void givenStream_whenPrependingObject_thenPrepended() {
        Stream<Integer> anStream = Stream.of(1, 2, 3, 4, 5);

        Stream<Integer> newStream = Stream.concat(Stream.of(99), anStream);

        assertEquals(newStream.findFirst()
          .get(), (Integer) 99);
    }    
}
