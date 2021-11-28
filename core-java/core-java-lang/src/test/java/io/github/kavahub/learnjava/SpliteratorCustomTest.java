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

/**
 * {@link Spliterator} 自定义
 * 
 * <p>
 * {@code Spliterator} 是一个可分割迭代器(splitable iterator)，可以和iterator顺序遍历迭代器一起看。jdk1.8发布后，
 * 对于并行处理的能力大大增强，{@code Spliterator} 就是为了并行遍历元素而设计的一个迭代器
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SpliteratorCustomTest {
    @Test
    public void whenCustomTakeWhileIsCalled_ThenCorrectItemsAreReturned() {
        Stream<String> initialStream = Stream.of("cat", "dog", "elephant", "fox", "rabbit", "duck");

        CustomSpliterator<String> customSpliterator = new CustomSpliterator<>(initialStream.spliterator(),
                x -> x.length() % 2 != 0);

        List<String> result = StreamSupport.stream(customSpliterator, false).collect(Collectors.toList());

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
