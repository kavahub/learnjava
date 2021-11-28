package io.github.kavahub.learnjava.util;

import java.time.Duration;
import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import static io.github.kavahub.learnjava.util.RandomDateTimes.*;

/**
 * 
 * {@link RandomDateTimes} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class RandomDateTimesTest {
    @RepeatedTest(100)
    void givenNoRange_WhenGenTimestamp_ShouldGenerateRandomTimestamps() {
        Instant random = timestamp();

        Assertions.assertThat(random).isBetween(Instant.MIN, Instant.MAX);
    }

    @RepeatedTest(100)
    void givenARange_WhenGenTimestamp_ShouldBeInRange() {
        Instant hundredYearsAgo = Instant.now().minus(Duration.ofDays(100 * 365));
        Instant tenDaysAgo = Instant.now().minus(Duration.ofDays(10));

        Instant random = between(hundredYearsAgo, tenDaysAgo);
        Assertions.assertThat(random).isBetween(hundredYearsAgo, tenDaysAgo);
    }    
}
