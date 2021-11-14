package io.github.kavahub.learnjava;

import java.time.LocalDate;
import java.time.Month;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

public class RandomDatesTest {
    @RepeatedTest(100)
    void givenNoRange_WhenGenDate_ShouldGenerateRandomDates() {
        LocalDate randomDay = RandomDates.date();

        Assertions.assertThat(randomDay).isAfter(LocalDate.MIN).isBefore(LocalDate.MAX);
    }

    @RepeatedTest(100)
    void givenARange_WhenGenDate_ShouldBeInRange() {
        LocalDate start = LocalDate.of(1989, Month.OCTOBER, 14);
        LocalDate end = LocalDate.now();

        // 测试时，存在等于start的情况
        LocalDate random = RandomDates.between(start, end);
        Assertions.assertThat(random).isAfterOrEqualTo(start).isBefore(end);
    }    
}
