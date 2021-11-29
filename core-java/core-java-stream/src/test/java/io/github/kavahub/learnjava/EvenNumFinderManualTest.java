package io.github.kavahub.learnjava;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * 
 * 查找奇数
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class EvenNumFinderManualTest {
    @Test
    public void givenRange_whenStream_thenFindEvenNum() {
        IntStream.range(10, 100).filter(e -> e%2 == 0)
            .forEach(e -> System.out.println(e));
    }
}
