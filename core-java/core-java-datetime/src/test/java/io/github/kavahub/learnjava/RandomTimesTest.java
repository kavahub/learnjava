package io.github.kavahub.learnjava;

import java.time.LocalTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

public class RandomTimesTest {
    @RepeatedTest(100)
    void givenARange_WhenGenTime_ShouldBeInRange() {
        LocalTime morning = LocalTime.of(8, 30);
        LocalTime randomTime = RandomTimes.between(LocalTime.MIDNIGHT, morning);

        Assertions.assertThat(randomTime)
                .isAfter(LocalTime.MIDNIGHT).isBefore(morning)
                .isAfter(LocalTime.MIN).isBefore(LocalTime.MAX);
    }    
}
