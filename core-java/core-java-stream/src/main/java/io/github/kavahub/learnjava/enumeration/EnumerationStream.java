package io.github.kavahub.learnjava.enumeration;

import java.util.Enumeration;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lombok.experimental.UtilityClass;

/**
 * 
 * {@link Enumeration} 集合支持流
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class EnumerationStream {
    public <T> Stream<T> of(Enumeration<T> enumeration) {
        // ORDERED（有序）,DISTINCT（不重复），SORTED（可排序），
        // SIZED（明确集合大小），NONNULL（所有元素非空），IMMUTABLE（不可更改），
        // CONCURRENT（支持并发）和SUBSIZED（拆分的子Spliterator是SIZED）
        EnumerationSpliterator<T> spliterator = new EnumerationSpliterator<T>(Long.MAX_VALUE, Spliterator.ORDERED,
                enumeration);
        // 不支持多线程
        Stream<T> stream = StreamSupport.stream(spliterator, false);

        return stream;
    }
}
