package io.github.kavahub.learnjava;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Duration 是在 Java 8中加入的，主要是用来计算日期，差值之类的。
 * 
 * <p>
 * Duration 被声明final（immutable），并且线程安全。
 */
public class JavaDurationTest {
    @Test
    public void givenATimePlus30Seconds_whenRequestingDuration_thenExpect30() {
        LocalTime initialTime = LocalTime.of(6, 30, 0);
        LocalTime finalTime = initialTime.plus(Duration.ofSeconds(30));

        long seconds = Duration.between(initialTime, finalTime)
            .getSeconds();

        assertThat(seconds).isEqualTo(30);
    }

    @Test
    public void givenATimePlus30Seconds_whenRequestingSecondsBetween_thenExpect30() {
        LocalTime initialTime = LocalTime.of(6, 30, 0);
        LocalTime finalTime = initialTime.plus(Duration.ofSeconds(30));

        long seconds = ChronoUnit.SECONDS.between(initialTime, finalTime);

        assertThat(seconds).isEqualTo(30);
    }

    @Test
    public void test2() {
        Instant start = Instant.parse("2017-10-03T10:15:30.00Z");
        Instant end = Instant.parse("2017-10-03T10:16:30.00Z");

        Duration duration = Duration.between(start, end);

        assertFalse(duration.isNegative());

        assertEquals(60, duration.getSeconds());
        assertEquals(1, duration.toMinutes());

        Duration fromDays = Duration.ofDays(1);
        assertEquals(86400, fromDays.getSeconds());

        Duration fromMinutes = Duration.ofMinutes(60);
        assertEquals(1, fromMinutes.toHours());

        assertEquals(120, duration.plusSeconds(60)
            .getSeconds());
        assertEquals(30, duration.minusSeconds(30)
            .getSeconds());

        assertEquals(120, duration.plus(60, ChronoUnit.SECONDS)
            .getSeconds());
        assertEquals(30, duration.minus(30, ChronoUnit.SECONDS)
            .getSeconds());

    }    
}
