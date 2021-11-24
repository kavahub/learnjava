package io.github.kavahub.learnjava.use;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.use.UseLocalDate.*;

public class UseLocalDateTest {
    @Test
    public void givenValues_whenUsingFactoryOf_thenLocalDate() {
        assertEquals("2016-05-10", getLocalDateUsingFactoryOfMethod(2016, 5, 10)
            .toString());
    }

    @Test
    public void givenString_whenUsingParse_thenLocalDate() {
        assertEquals("2016-05-10", getLocalDateUsingParseMethod("2016-05-10")
            .toString());
    }

    @Test
    public void whenUsingClock_thenLocalDate() {
        assertEquals(LocalDate.now(), getLocalDateFromClock());
    }

    @Test
    public void givenDate_whenUsingPlus_thenNextDay() {
        assertEquals(LocalDate.now()
            .plusDays(1), getNextDay(LocalDate.now()));
    }

    @Test
    public void givenDate_whenUsingMinus_thenPreviousDay() {
        assertEquals(LocalDate.now()
            .minusDays(1), getPreviousDay(LocalDate.now()));
    }

    @Test
    public void givenToday_whenUsingGetDayOfWeek_thenDayOfWeek() {
        assertEquals(DayOfWeek.SUNDAY, getDayOfWeek(LocalDate.parse("2016-05-22")));
    }

    @Test
    public void givenToday_whenUsingWithTemporalAdjuster_thenFirstDayOfMonth() {
        assertEquals(1, getFirstDayOfMonth()
            .getDayOfMonth());
    }

    @Test
    public void givenLocalDate_whenUsingAtStartOfDay_thenReturnMidnight() {
        assertEquals(LocalDateTime.parse("2016-05-22T00:00:00"), getStartOfDay(LocalDate.parse("2016-05-22")));
    }

    @Test
    public void givenLocalDate_whenSettingStartOfDay_thenReturnMidnightInAllCases() {
        LocalDate given = LocalDate.parse("2018-06-23");

        LocalDateTime startOfDayWithMethod = getStartOfDay(given);
        LocalDateTime startOfDayOfLocalDate = getStartOfDayOfLocalDate(given);
        LocalDateTime startOfDayWithMin = getStartOfDayAtMinTime(given);
        LocalDateTime startOfDayWithMidnight = getStartOfDayAtMidnightTime(given);

       assertThat(startOfDayWithMethod).isEqualTo(startOfDayWithMin)
            .isEqualTo(startOfDayWithMidnight)
            .isEqualTo(startOfDayOfLocalDate)
            .isEqualTo(LocalDateTime.parse("2018-06-23T00:00:00"));
        assertThat(startOfDayWithMin.toLocalTime()).isEqualTo(LocalTime.MIDNIGHT);
        assertThat(startOfDayWithMin.toString()).isEqualTo("2018-06-23T00:00");
    }

    @Test
    public void givenLocalDate_whenSettingEndOfDay_thenReturnLastMomentOfDay() {
        LocalDate given = LocalDate.parse("2018-06-23");

        LocalDateTime endOfDayWithMax = getEndOfDay(given);
        LocalDateTime endOfDayFromLocalTime = getEndOfDayFromLocalTime(given);

        assertThat(endOfDayWithMax).isEqualTo(endOfDayFromLocalTime);
        assertThat(endOfDayWithMax.toLocalTime()).isEqualTo(LocalTime.MAX);
        assertThat(endOfDayWithMax.toString()).isEqualTo("2018-06-23T23:59:59.999999999");
    }

    @Test
    public void givenTheYear2000_whenCheckingForLeapYear_thenReturnTrue() {
        LocalDate given = LocalDate.parse("2000-06-23");

        boolean leapYear = isLeapYear(given);

        assertThat(leapYear).isEqualTo(true);
    }

    @Test
    public void givenTheYear2004_whenCheckingForLeapYear_thenReturnTrue() {
        LocalDate given = LocalDate.parse("2004-06-23");

        boolean leapYear = isLeapYear(given);

        assertThat(leapYear).isEqualTo(true);
    }

    @Test
    public void givenTheYear2019_whenCheckingForLeapYear_thenReturnFalse() {
        LocalDate given = LocalDate.parse("2019-06-23");

        boolean leapYear = isLeapYear(given);

        assertThat(leapYear).isEqualTo(false);
    }    
}
