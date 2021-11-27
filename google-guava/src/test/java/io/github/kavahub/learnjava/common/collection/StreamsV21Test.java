package io.github.kavahub.learnjava.common.collection;

import static io.github.kavahub.learnjava.common.collection.StreamUtility.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.google.common.collect.Streams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Streams} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class StreamsV21Test {
    private List<Integer> numbers;

    @BeforeEach
    public void setUp() {
        numbers = IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList());
    }

    @Test
    public void createStreamsWithCollection() {
        //Deprecated API to create stream from collection
        @SuppressWarnings("deprecation")
        Stream<Integer> streamFromCollection = Streams.stream(numbers);

        //Assert.assertNotNull(streamFromCollection);
        assertStreamEquals(streamFromCollection, numbers.stream());
    }

    @Test
    public void createStreamsWithIterable() {
        Iterable<Integer> numbersIterable = numbers;

        Stream<Integer> streamFromIterable = Streams.stream(numbersIterable);

        assertNotNull(streamFromIterable);
        assertStreamEquals(streamFromIterable, numbers.stream());
    }

    @Test
    public void createStreamsWithIterator() {
        Iterator<Integer> numbersIterator = numbers.iterator();

        Stream<Integer> streamFromIterator = Streams.stream(numbersIterator);

        assertNotNull(streamFromIterator);
        assertStreamEquals(streamFromIterator, numbers.stream());
    }

    @Test
    public void createStreamsWithOptional() {

        Stream<Integer> streamFromOptional = Streams.stream(Optional.of(1));

        assertNotNull(streamFromOptional);
        assertEquals(streamFromOptional.count(), 1);
    }

    @Test
    public void createStreamsWithOptionalLong() {

        LongStream streamFromOptionalLong = Streams.stream(OptionalLong.of(1));

        assertNotNull(streamFromOptionalLong);
        assertEquals(streamFromOptionalLong.count(), 1);
    }

    @Test
    public void createStreamsWithOptionalInt() {

        IntStream streamFromOptionalInt = Streams.stream(OptionalInt.of(1));

        //Assert.assertNotNull(streamFromOptionalInt);
        assertEquals(streamFromOptionalInt.count(), 1);
    }

    @Test
    public void createStreamsWithOptionalDouble() {

        DoubleStream streamFromOptionalDouble = Streams.stream(OptionalDouble.of(1.0));

        //Assert.assertNotNull(streamFromOptionalDouble);
        assertEquals(streamFromOptionalDouble.count(), 1);

    }

    @Test
    public void concatStreamsOfSameType() {
        List<Integer> oddNumbers = Arrays
          .asList(1, 3, 5, 7, 9, 11, 13, 15, 17, 19);
        List<Integer> evenNumbers = Arrays
          .asList(2, 4, 6, 8, 10, 12, 14, 16, 18, 20);

        Stream<Integer> combinedStreams = Streams.concat(oddNumbers.stream(), evenNumbers.stream());

        //Assert.assertNotNull(combinedStreams);
        assertStreamEquals(combinedStreams, Stream.concat(oddNumbers.stream(), evenNumbers.stream()));
    }

    @Test
    public void concatStreamsOfTypeLongStream() {
        LongStream combinedStreams = Streams.concat(LongStream.range(1, 21), LongStream.range(21, 40));

        assertNotNull(combinedStreams);
        assertStreamEquals(combinedStreams, LongStream.range(1, 40));
    }

    @Test
    public void concatStreamsOfTypeIntStream() {
        IntStream combinedStreams = Streams.concat(IntStream.range(1, 20), IntStream.range(21, 40));

        assertNotNull(combinedStreams);
        assertStreamEquals(combinedStreams, IntStream.concat(IntStream.range(1, 20), IntStream.range(21, 40)));
    }

    @Test
    public void findLastOfStream() {
        Optional<Integer> lastElement = Streams.findLast(numbers.stream());

        assertEquals(lastElement.get(), numbers.get(19));
    }

    @Test
    public void mapWithIndexTest() {
        Stream<String> stringStream = Stream.of("a", "b", "c");

        Stream<String> mappedStream = Streams.mapWithIndex(stringStream, (str, index) -> str + ":" + index);

        //Assert.assertNotNull(mappedStream);
        assertEquals(mappedStream
          .findFirst()
          .get(), "a:0");

    }

    @Test
    public void streamsZipTest() {
        Stream<String> stringSream = Stream.of("a", "b", "c");
        Stream<Integer> intStream = Stream.of(1, 2, 3);
        Stream<String> mappedStream = Streams.zip(stringSream, intStream, (str, index) -> str + ":" + index);

        //Assert.assertNotNull(mappedStream);
        assertEquals(mappedStream
          .findFirst()
          .get(), "a:1");

    }
   
}
