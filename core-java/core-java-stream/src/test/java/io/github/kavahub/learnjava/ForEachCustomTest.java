package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class ForEachCustomTest {
    @Test
    public void whenCustomForEachIsCalled_ThenCorrectItemsAreReturned() {
        Stream<String> initialStream = Stream.of("cat", "dog", "elephant", "fox", "rabbit", "duck");
        List<String> result = new ArrayList<>();

        CustomForEach.forEach(initialStream, (elem, breaker) -> {
            if (elem.length() % 2 == 0) {
                breaker.stop();
            } else {
                result.add(elem);
            }
        });

        assertEquals(Arrays.asList("cat", "dog"), result);
    }    

    public static class CustomForEach {   
        public static <T> void forEach(Stream<T> stream, BiConsumer<T, Breaker> consumer) {
            Spliterator<T> spliterator = stream.spliterator();
            boolean hadNext = true;
            Breaker breaker = new Breaker();
    
            while (hadNext && !breaker.get()) {
                hadNext = spliterator.tryAdvance(elem -> {
                    consumer.accept(elem, breaker);
                });
            }
        }
    }

    public static class Breaker {
        private boolean shouldBreak = false;

        public void stop() {
            shouldBreak = true;
        }

        boolean get() {
            return shouldBreak;
        }
    }
}
