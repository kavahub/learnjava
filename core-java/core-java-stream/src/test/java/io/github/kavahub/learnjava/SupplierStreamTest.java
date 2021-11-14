package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class SupplierStreamTest {
    @Test // (expected = IllegalStateException.class)
    public void givenStream_whenStreamUsedTwice_thenThrowException() {
        Stream<String> stringStream = Stream.of("A", "B", "C", "D");
        Optional<String> result1 = stringStream.findAny();
        System.out.println(result1.get());

        // stream 只能使用一次
        assertThrows(IllegalStateException.class, () -> stringStream.findFirst());
    }

    @Test
    public void givenStream_whenUsingSupplier_thenNoExceptionIsThrown() {
        Supplier<Stream<String>> streamSupplier = () -> Stream.of("A", "B", "C", "D");
        Optional<String> result1 = streamSupplier.get().findAny();
        System.out.println(result1.get());
        Optional<String> result2 = streamSupplier.get().findFirst();
        System.out.println(result2.get());
    }
}
