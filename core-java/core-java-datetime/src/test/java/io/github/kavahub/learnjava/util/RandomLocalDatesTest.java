package io.github.kavahub.learnjava.util;

import java.time.LocalDate;
import java.time.Month;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import static io.github.kavahub.learnjava.util.RandomLocalDates.*;

/**
 * 
 * {@link RandomLocalDates} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class RandomLocalDatesTest {
    @RepeatedTest(100)
    void givenNoRange_WhenGenDate_ShouldGenerateRandomDates() {
        LocalDate randomDay = date();

        Assertions.assertThat(randomDay).isAfter(LocalDate.MIN).isBefore(LocalDate.MAX);
    }

    @RepeatedTest(100)
    void givenARange_WhenGenDate_ShouldBeInRange() {
        LocalDate start = LocalDate.of(1989, Month.OCTOBER, 14);
        LocalDate end = LocalDate.now();

        // 测试时，存在等于start的情况, 建议使用isBetween
        LocalDate random = between(start, end);
        Assertions.assertThat(random).isAfterOrEqualTo(start).isBefore(end);
    }    
}
