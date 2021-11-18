package io.github.kavahub.learnjava;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

public class LegacyRandomDateTimesTest {
    private static final Date MIN_DATE = new Date(Long.MIN_VALUE);
    private static final Date MAX_DATE = new Date(Long.MAX_VALUE);

    @RepeatedTest(100)
    void givenARange_WhenGenTimestamp_ShouldBeInRange() {
        long aDay = TimeUnit.DAYS.toMillis(1);
        long now = new Date().getTime();

        Date hundredYearsAgo = new Date(now - aDay * 365 * 100);
        Date tenDaysAgo = new Date(now - aDay * 10);

        Date random = LegacyRandomDateTimes.between(hundredYearsAgo, tenDaysAgo);
        Assertions.assertThat(random).isBetween(hundredYearsAgo, tenDaysAgo);
    }

    @RepeatedTest(100)
    void givenNoRange_WhenGenTimestamp_ShouldGenerateRandomTimestamps() {
        Date random = LegacyRandomDateTimes.timestamp();

        Assertions.assertThat(random)
                .isNotNull()
                .isBetween(MIN_DATE, MAX_DATE);
    }    
}