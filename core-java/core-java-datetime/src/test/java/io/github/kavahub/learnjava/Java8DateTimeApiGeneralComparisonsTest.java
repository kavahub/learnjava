package io.github.kavahub.learnjava;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 
 * Java提供的日期比较方法示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class Java8DateTimeApiGeneralComparisonsTest {
    @Test
    public void givenLocalDates_whenComparing_thenAssertsPass() {
        LocalDate firstDate = LocalDate.of(2019, 8, 10);
        LocalDate secondDate = LocalDate.of(2019, 7, 1);
        LocalDate thirdDate = LocalDate.of(2019, 7, 1); // same date as secondDate

        assertTrue(firstDate.isAfter(secondDate));
        assertFalse(firstDate.isBefore(secondDate));

        assertFalse(firstDate.isEqual(secondDate));
        assertFalse(firstDate.equals(secondDate));

        assertThat(firstDate.compareTo(secondDate)).isEqualTo(1);
        assertThat(secondDate.compareTo(firstDate)).isEqualTo(-1);

        assertFalse(secondDate.isAfter(thirdDate));
        assertFalse(secondDate.isBefore(thirdDate));
        assertTrue(secondDate.isEqual(thirdDate));
        assertTrue(secondDate.equals(thirdDate));
        assertThat(secondDate.compareTo(thirdDate)).isEqualTo(0);
    }

    @Test
    public void givenLocalDateTimes_whenComparing_thenAssertsPass() {
        LocalDateTime firstTimestamp = LocalDateTime.of(2019, 8, 10, 11, 30, 0);
        LocalDateTime secondTimestamp = LocalDateTime.of(2019, 8, 10, 11, 15, 0);
        LocalDateTime thirdTimestamp = LocalDateTime.of(2019, 8, 10, 11, 15, 0); // same as secondTimestamp

        assertTrue(firstTimestamp.isAfter(secondTimestamp));
        assertFalse(firstTimestamp.isBefore(secondTimestamp));

        assertFalse(firstTimestamp.isEqual(secondTimestamp));
        assertFalse(firstTimestamp.equals(secondTimestamp));

        assertThat(firstTimestamp.compareTo(secondTimestamp)).isEqualTo(1);
        assertThat(secondTimestamp.compareTo(firstTimestamp)).isEqualTo(-1);

        assertFalse(secondTimestamp.isAfter(thirdTimestamp));
        assertFalse(secondTimestamp.isBefore(thirdTimestamp));
        assertTrue(secondTimestamp.isEqual(thirdTimestamp));
        assertThat(secondTimestamp.compareTo(thirdTimestamp)).isEqualTo(0);
    }

    @Test
    public void givenZonedDateTimes_whenComparing_thenAssertsPass() {
        ZonedDateTime timeInNewYork = ZonedDateTime.of(2019, 8, 10, 8, 0, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime timeInBerlin = ZonedDateTime.of(2019, 8, 10, 14, 0, 0, 0, ZoneId.of("Europe/Berlin"));

        assertFalse(timeInNewYork.isAfter(timeInBerlin));
        assertFalse(timeInNewYork.isBefore(timeInBerlin));

        assertTrue(timeInNewYork.isEqual(timeInBerlin));
        assertFalse(timeInNewYork.equals(timeInBerlin));

        // 不管时区
        assertThat(timeInNewYork.compareTo(timeInBerlin)).isEqualTo(-1);
    }

    @Test
    public void givenLocalTimes_whenComparing_thenAssertsPass() {
        LocalTime firstTime = LocalTime.of(8, 30);
        LocalTime secondTime = LocalTime.of(9, 45);

        assertFalse(firstTime.isAfter(secondTime));
        assertTrue(firstTime.isBefore(secondTime));

        assertFalse(firstTime.equals(secondTime));

        assertThat(firstTime.compareTo(secondTime)).isEqualTo(-1);
    }

    @Test
    public void givenMinMaxLocalTimes_whenComparing_thenAssertsPass() {
        LocalTime minTime = LocalTime.MIN;
        LocalTime time = LocalTime.of(8, 30);
        LocalTime maxTime = LocalTime.MAX;

        assertTrue(minTime.isBefore(time));
        assertTrue(time.isBefore(maxTime));
    }   
}
