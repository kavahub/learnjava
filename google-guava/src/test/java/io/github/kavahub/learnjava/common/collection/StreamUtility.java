package io.github.kavahub.learnjava.common.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Iterator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamUtility {
    public static <T> boolean assertStreamEquals(Stream<T> stream1, Stream<T> stream2) {

        Iterator<T> iterator1 = stream1.iterator();
        Iterator<T> iterator2 = stream2.iterator();

        while (iterator1.hasNext()) {
            assertEquals(iterator1.next(), iterator2.next());
        }

        assertFalse(iterator2.hasNext());

        return true;
    }

    public static boolean assertStreamEquals(LongStream stream1, LongStream stream2) {

        Iterator<?> iterator1 = stream1.iterator();
        Iterator<?> iterator2 = stream2.iterator();

        while (iterator1.hasNext()) {
            assertEquals(iterator1.next(), iterator2.next());
        }

        assertFalse(iterator2.hasNext());

        return true;
    }

    public static boolean assertStreamEquals(DoubleStream stream1, DoubleStream stream2) {

        Iterator<?> iterator1 = stream1.iterator();
        Iterator<?> iterator2 = stream2.iterator();

        while (iterator1.hasNext()) {
            assertEquals(iterator1.next(), iterator2.next());
        }

        assertFalse(iterator2.hasNext());

        return true;
    }

    public static boolean assertStreamEquals(IntStream stream1, IntStream stream2) {

        Iterator<?> iterator1 = stream1.iterator();
        Iterator<?> iterator2 = stream2.iterator();

        while (iterator1.hasNext()) {
            assertEquals(iterator1.next(), iterator2.next());
        }

        assertFalse(iterator2.hasNext());

        return true;
    }
}
