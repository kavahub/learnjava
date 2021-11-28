package io.github.kavahub.learnjava.util;

import java.time.LocalTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import static io.github.kavahub.learnjava.util.RandomLocalTimes.*;

/**
 * 
 * {@link RandomLocalTimes} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class RandomLocalTimesTest {
    @RepeatedTest(100)
    void givenNoRange_WhenGenDate_ShouldGenerateRandomDates() {
        LocalTime randomTime = time();

        Assertions.assertThat(randomTime).isAfter(LocalTime.MIN).isBefore(LocalTime.MAX);
    }

    @RepeatedTest(100)
    void givenARange_WhenGenTime_ShouldBeInRange() {
        LocalTime morning = LocalTime.of(8, 30);
        LocalTime randomTime = between(LocalTime.MIDNIGHT, morning);

        // 测试时，存在等于start的情况, 建议使用isBetween
        Assertions.assertThat(randomTime)
                .isAfterOrEqualTo(LocalTime.MIDNIGHT).isBefore(morning)
                .isAfterOrEqualTo(LocalTime.MIN).isBefore(LocalTime.MAX);
    }    
}
