package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.stream.Stream;

import com.google.common.base.Supplier;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * {@link Optional} 链
 */
public class OptionalChainingTest {
    private static boolean getEmptyEvaluated;
    private static boolean getHelloEvaluated;
    private static boolean getByeEvaluated;

    @BeforeAll
    public static void setUp() {
        getEmptyEvaluated = false;
        getHelloEvaluated = false;
        getByeEvaluated = false;
    }

    @Test
    public void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturned() {
        Optional<String> found = Stream.of(getEmpty(), getHello(), getBye())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertEquals(getHello(), found);
    }

    @Test
    public void givenTwoEmptyOptionals_whenChaining_thenEmptyOptionalIsReturned() {
        Optional<String> found = Stream.of(getEmpty(), getEmpty())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertFalse(found.isPresent());
    }

    @Test
    public void givenTwoEmptyOptionals_whenChaining_thenDefaultIsReturned() {
        String found = Stream.<Supplier<Optional<String>>>of(
                () -> createOptional("empty"),
                () -> createOptional("empty")
        )
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseGet(() -> "default");

        assertEquals("default", found);
    }

    @Test
    public void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturnedAndRestNotEvaluated() {
        Optional<String> found = Stream.<Supplier<Optional<String>>>of(this::getEmpty, this::getHello, this::getBye)
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertTrue(getEmptyEvaluated);
        assertTrue(getHelloEvaluated);
        assertFalse(getByeEvaluated);
        assertEquals(getHello(), found);
    }

    @Test
    public void givenTwoOptionalsReturnedByOneArgMethod_whenChaining_thenFirstNonEmptyIsReturned() {
        Optional<String> found = Stream.<Supplier<Optional<String>>>of(
                () -> createOptional("empty"),
                () -> createOptional("hello")
        )
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        assertEquals(createOptional("hello"), found);
    }

    private Optional<String> getEmpty() {
        getEmptyEvaluated = true;
        return Optional.empty();
    }

    private Optional<String> getHello() {
        getHelloEvaluated = true;
        return Optional.of("hello");
    }

    private Optional<String> getBye() {
        getByeEvaluated = true;
        return Optional.of("bye");
    }

    private Optional<String> createOptional(String input) {
        if (input == null || "".equals(input) || "empty".equals(input)) {
            return Optional.empty();
        }

        return Optional.of(input);
    }   
}
