package io.github.kavahub.learnjava.common.collection;

import static io.github.kavahub.learnjava.common.collection.StreamUtility.assertStreamEquals;
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

import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Streams} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class StreamsTest {
    private List<String> names;
    private List<Integer> ages;
    private List<String> expectedOutput;
    private List<Integer> numbers;

    @BeforeEach
    public void setUp() throws Exception {
        names = Arrays.asList("John", "Jane", "Jack", "Dennis");
        ages = Arrays.asList(24, 25, 27);
        expectedOutput = Arrays.asList("John:24", "Jane:25", "Jack:27");
        numbers = IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList());
    }

    @Test
    public void zipCollectionUsingGuava21() {
        List<String> output = Streams
          .zip(names.stream(), ages.stream(), (name, age) -> name + ":" + age)
          .collect(Collectors.toList());

        assertEquals(output, expectedOutput);
    }

    @Test
    public void zipCollectionUsingIntStream() {
        List<String> output = IntStream
          .range(0, Math.min(names.size(), ages.size()))
          .mapToObj(i -> names.get(i) + ":" + ages.get(i))
          .collect(Collectors.toList());

        assertEquals(output, expectedOutput);
    }

    @Test
    public void zipCollectionUsingJool() {
        Seq<String> output = Seq
          .of("John", "Jane", "Jack")
          .zip(Seq.of(24, 25, 27), (x, y) -> x + ":" + y);

        assertEquals(output.toList(), expectedOutput);
    }

    @Test
    public void zipCollectionUsingJoolTuple() {
        Seq<Tuple2<String, Integer>> output = Seq
          .of("John", "Jane", "Dennis")
          .zip(Seq.of(24, 25, 27));

        Tuple2<String, Integer> element1 = new Tuple2<String, Integer>("John", 24);
        Tuple2<String, Integer> element2 = new Tuple2<String, Integer>("Jane", 25);
        Tuple2<String, Integer> element3 = new Tuple2<String, Integer>("Dennis", 27);

        List<Tuple2<?, ?>> expectedOutput = Arrays.asList(element1, element2, element3);
        assertEquals(output.collect(Collectors.toList()), expectedOutput);
    }

    @Test
    public void zipCollectionUsingJoolWithIndex() {
        Seq<Tuple2<String, Long>> output = Seq
          .of("John", "Jane", "Dennis")
          .zipWithIndex();

        Tuple2<String, Long> element1 = new Tuple2<>("John", 0L);
        Tuple2<String, Long> element2 = new Tuple2<>("Jane", 1L);
        Tuple2<String, Long> element3 = new Tuple2<>("Dennis", 2L);

        List<Tuple2<?, ?>> expectedOutput = Arrays.asList(element1, element2, element3);
        assertEquals(output.collect(Collectors.toList()), expectedOutput);
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

        //assertNotNull(streamFromOptionalInt);
        assertEquals(streamFromOptionalInt.count(), 1);
    }

    @Test
    public void createStreamsWithOptionalDouble() {

        DoubleStream streamFromOptionalDouble = Streams.stream(OptionalDouble.of(1.0));

        //assertNotNull(streamFromOptionalDouble);
        assertEquals(streamFromOptionalDouble.count(), 1);

    }

    @Test
    public void concatStreamsOfSameType() {
        List<Integer> oddNumbers = Arrays
          .asList(1, 3, 5, 7, 9, 11, 13, 15, 17, 19);
        List<Integer> evenNumbers = Arrays
          .asList(2, 4, 6, 8, 10, 12, 14, 16, 18, 20);

        Stream<Integer> combinedStreams = Streams.concat(oddNumbers.stream(), evenNumbers.stream());

        //assertNotNull(combinedStreams);
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

        //assertNotNull(mappedStream);
        assertEquals(mappedStream
          .findFirst()
          .get(), "a:0");

    }

    @Test
    public void streamsZipTest() {
        Stream<String> stringSream = Stream.of("a", "b", "c");
        Stream<Integer> intStream = Stream.of(1, 2, 3);
        Stream<String> mappedStream = Streams.zip(stringSream, intStream, (str, index) -> str + ":" + index);

        //assertNotNull(mappedStream);
        assertEquals(mappedStream
          .findFirst()
          .get(), "a:1");

    }
}
