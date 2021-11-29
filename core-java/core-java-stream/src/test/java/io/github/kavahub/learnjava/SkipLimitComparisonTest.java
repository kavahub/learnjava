package io.github.kavahub.learnjava;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 
 * skip， limit 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SkipLimitComparisonTest {
    @Test
    public void giveStream_whenSkip() {
        List<Integer> numberSet = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .filter(i -> i % 2 == 0)
            .skip(2)
            .collect(Collectors.toList());
        Assertions.assertThat(numberSet).containsExactly(6, 8, 10);

    }

    @Test
    public void giveStream_whenLimit() {
        List<Integer> numberSet = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .filter(i -> i % 2 == 0)
            .limit(2)
            .collect(Collectors.toList());
        Assertions.assertThat(numberSet).containsExactly(2, 4);            
    }

    @Test
    public void limitInfiniteStreamExample() {
        // 从3开始数数
        List<Integer> numberSet = Stream.iterate(3, i -> i + 1)
            .filter(i -> i % 2 == 0)
            .limit(5)
            .collect(Collectors.toList());
        Assertions.assertThat(numberSet).containsExactly(4, 6, 8, 10, 12);      
    } 
}
