package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

public class SpliteratorCustomTest {
    @Test
    public void whenCustomTakeWhileIsCalled_ThenCorrectItemsAreReturned() {
        Stream<String> initialStream = Stream.of("cat", "dog", "elephant", "fox", "rabbit", "duck");

        CustomSpliterator<String> customSpliterator = new CustomSpliterator<>(initialStream.spliterator(), x -> x.length() % 2 != 0);

        List<String> result =  StreamSupport.stream(customSpliterator, false).collect(Collectors.toList());

        assertEquals(Arrays.asList("cat", "dog"), result);
    }


    
    public static class CustomSpliterator<T> extends Spliterators.AbstractSpliterator<T> {

        private Spliterator<T> splitr;
        private Predicate<T> predicate;
        private boolean isMatched = true;
    
        public CustomSpliterator(Spliterator<T> splitr, Predicate<T> predicate) {
            super(splitr.estimateSize(), 0);
            this.splitr = splitr;
            this.predicate = predicate;
        }
    
        @Override
        public boolean tryAdvance(Consumer<? super T> consumer) {
            boolean hadNext = splitr.tryAdvance(elem -> {
                if (predicate.test(elem) && isMatched) {
                    consumer.accept(elem);
                } else {
                    isMatched = false;
                }
            });
            return hadNext && isMatched;
        }
    }
}
