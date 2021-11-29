package io.github.kavahub.learnjava.enhance;

import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;

/**
 * 
 * 可中断的ForEach
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class ForEachBreaker {
    public <T> void forEach(Stream<T> stream, BiConsumer<T, Breaker> consumer) {
        Spliterator<T> spliterator = stream.spliterator();
        boolean hadNext = true;
        Breaker breaker = new Breaker();

        while (hadNext && !breaker.get()) {
            hadNext = spliterator.tryAdvance(elem -> {
                consumer.accept(elem, breaker);
            });
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
