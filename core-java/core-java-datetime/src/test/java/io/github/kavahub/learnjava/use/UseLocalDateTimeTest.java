package io.github.kavahub.learnjava.use;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.use.UseLocalDateTime.*;

/**
 * 
 * {@link UseLocalDateTime} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class UseLocalDateTimeTest {

    @Test
    public void givenString_whenUsingParse_thenLocalDateTime() {
        LocalDateTime givenLocalDateTime = getLocalDateTimeUsingParseMethod("2016-05-10T06:30");
        
        assertEquals(LocalDate.of(2016, Month.MAY, 10), givenLocalDateTime.toLocalDate());
        assertEquals(LocalTime.of(6, 30), givenLocalDateTime.toLocalTime());
    }

    @Test
    public void givenLocalDateTime_whenSettingEndOfDay_thenReturnLastMomentOfDay() {
        LocalDateTime givenTimed = LocalDateTime.parse("2018-06-23T05:55:55");

        LocalDateTime endOfDayFromGivenDirectly = getEndOfDayFromLocalDateTimeDirectly(givenTimed);
        LocalDateTime endOfDayFromGiven = getEndOfDayFromLocalDateTime(givenTimed);

        assertThat(endOfDayFromGivenDirectly).isEqualTo(endOfDayFromGiven);
        assertThat(endOfDayFromGivenDirectly.toLocalTime()).isEqualTo(LocalTime.MAX);
        assertThat(endOfDayFromGivenDirectly.toString()).isEqualTo("2018-06-23T23:59:59.999999999");
    }

    @Test
    public void givenLocalDateTimeInFebruary_whenRequestingMonth_thenMonthIsFebruary() {
        LocalDateTime givenLocalDateTime = LocalDateTime.of(2015, Month.FEBRUARY, 20, 6, 30);

        assertThat(givenLocalDateTime.getMonth()).isEqualTo(Month.FEBRUARY);
    }

    @Test
    public void givenLocalDateTime_whenManipulating_thenResultIsAsExpected() {
        LocalDateTime givenLocalDateTime = LocalDateTime.parse("2015-02-20T06:30:00");

        LocalDateTime manipulatedLocalDateTime = givenLocalDateTime.plusDays(1);
        manipulatedLocalDateTime = manipulatedLocalDateTime.minusHours(2);

        assertThat(manipulatedLocalDateTime).isEqualTo(LocalDateTime.of(2015, Month.FEBRUARY, 21, 4, 30));
    }

    @Test
    public void whenRequestTimeFromEpoch_thenResultIsAsExpected() {
        LocalDateTime result = ofEpochSecond(1465817690, ZoneOffset.UTC);
        assertThat(result.toString()).isEqualTo("2016-06-13T11:34:50");

        result = ofEpochSecond(1465817690, ZoneOffset.of("+8"));
        assertThat(result.toString()).isEqualTo("2016-06-13T19:34:50");
    }
}
