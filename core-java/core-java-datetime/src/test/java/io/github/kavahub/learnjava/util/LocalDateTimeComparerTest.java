package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.LocalDateTimeComparer.*;

/**
 * 
 * {@link LocalDateTimeComparer} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class LocalDateTimeComparerTest {
    @Test
    void givenLocalDateTimes_whenIsSameDay_thenCompareTrue() {
        LocalDateTime firstTimestamp = LocalDateTime.of(2019, 8, 10, 11, 00, 0);
        LocalDateTime secondTimestamp = firstTimestamp.plusHours(5);
        LocalDateTime thirdTimestamp = firstTimestamp.plusDays(1);

        assertTrue(isSameDay(firstTimestamp, secondTimestamp));
        assertFalse(isSameDay(secondTimestamp, thirdTimestamp));
    }

    @Test
    void givenLocalDateAndLocalDateTime_whenIsSameDay_thenCompareTrue() {
        LocalDate localDate = LocalDate.of(2019, 8, 10);
        LocalDateTime localDateTime = LocalDateTime.of(2019, 8, 10, 11, 30, 0);

        assertTrue(isSameDay(localDateTime, localDate));
    }

    @Test
    void givenLocalDateTimes_whenIsSameHour_thenCompareTrue() {
        LocalDateTime firstTimestamp = LocalDateTime.of(2019, 8, 10, 8, 00, 0);
        LocalDateTime secondTimestamp = firstTimestamp.plusMinutes(15);
        LocalDateTime thirdTimestamp = firstTimestamp.plusHours(2);

        assertTrue(isSameHour(firstTimestamp, secondTimestamp));

        assertFalse(isSameHour(secondTimestamp, thirdTimestamp));
    }

    @Test
    void givenLocalDateTimes_whenIsSameMinute_thenCompareTrue() {
        LocalDateTime firstTimestamp = LocalDateTime.of(2019, 8, 10, 8, 15, 0);
        LocalDateTime secondTimestamp = firstTimestamp.plusSeconds(30);
        LocalDateTime thirdTimestamp = firstTimestamp.plusMinutes(5);

        assertTrue(isSameMinute(firstTimestamp, secondTimestamp));

        assertFalse(isSameMinute(secondTimestamp, thirdTimestamp));
    }

    @Test
    void givenZonedDateTimes_whenIsSameHour_thenCompareTrue() {
        ZonedDateTime zonedTimestamp = ZonedDateTime.of(2019, 8, 10, 8, 0, 0, 30,
          ZoneId.of("America/New_York"));
        ZonedDateTime zonedTimestampToCompare = ZonedDateTime.of(2019, 8, 10, 14, 0, 0, 0,
          ZoneId.of("Europe/Berlin"));

        assertTrue(isSameHour(zonedTimestamp, zonedTimestampToCompare));
    }

    @Test
    void givenZonedDateTimeAndLocalDateTime_whenIsSameHour_thenCompareTrue() {
        ZonedDateTime zonedTimestamp = ZonedDateTime.of(2019, 8, 10, 8, 15, 0, 0,
          ZoneId.of("America/New_York"));
        LocalDateTime localTimestamp = LocalDateTime.of(2019, 8, 10, 14, 20, 0);
        ZoneId zoneId = ZoneId.of("Europe/Berlin");

        assertTrue(isSameHour(zonedTimestamp, localTimestamp, zoneId));
    }  
}
