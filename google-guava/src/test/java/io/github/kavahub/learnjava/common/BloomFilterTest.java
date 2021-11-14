package io.github.kavahub.learnjava.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import org.junit.jupiter.api.Test;

/**
 * BloomFilter布隆过滤器, 可以理解为是一个m位的数组，它有k个相互独立的哈希函数
 * 
 * Funnel是Guava中定义的一个接口，它和PrimitiveSink配套使用，
 * 主要是把任意类型的数据转化成HashCode (byte数组)。Guava预定义了一些原生类型的Funnel，
 * 如String、Long、Integer等等
 * 
 * @see <a href=""></a>
 * 
 */
public class BloomFilterTest {
    @Test
    public void givenBloomFilter_whenAddNStringsToIt_thenShouldNotReturnAnyFalsePositive() {
        //when
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                500,
                0.01);

        //when
        filter.put(1);
        filter.put(2);
        filter.put(3);

        //then
        // the probability that it returns true, but is actually false is 1%
        assertThat(filter.mightContain(1)).isTrue();
        assertThat(filter.mightContain(2)).isTrue();
        assertThat(filter.mightContain(3)).isTrue();

        assertThat(filter.mightContain(100)).isFalse();
    }

    @Test
    public void givenBloomFilter_whenAddNStringsToItMoreThanDefinedExpectedInsertions_thenItWillReturnTrueForAlmostAllElements() {
        //when
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                5,
                0.01);

        //when
        IntStream.range(0, 100_000).forEach(filter::put);


        //then
        assertThat(filter.mightContain(1)).isTrue();
        assertThat(filter.mightContain(2)).isTrue();
        assertThat(filter.mightContain(3)).isTrue();
        assertThat(filter.mightContain(1_000_000)).isTrue();
    }
}
