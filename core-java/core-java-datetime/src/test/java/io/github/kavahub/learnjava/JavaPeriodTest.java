package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

/**
 * Period 和 Duration。两个类看表示时间量或两个日期之间的差.
 * 
 * <p>
 * 两者之间的差异为：Period基于日期值，而Duration基于时间值。
 */
public class JavaPeriodTest {
    @Test
    public void givenADatePlus5Days_whenRequestingPeriod_thenExpectFive() {
        LocalDate initialDate = LocalDate.parse("2007-05-10");
        LocalDate finalDate = initialDate.plus(Period.ofDays(5));

        int days = Period.between(initialDate, finalDate).getDays();

        assertThat(days).isEqualTo(5);
    }

    @Test
    public void givenADatePlus5Days_whenRequestingDaysBetween_thenExpectFive() {
        LocalDate initialDate = LocalDate.parse("2007-05-10");
        LocalDate finalDate = initialDate.plus(Period.ofDays(5));

        long days = ChronoUnit.DAYS.between(initialDate, finalDate);

        assertThat(days).isEqualTo(5);
    }

    @Test
    public void whenTestPeriod_thenOk() {
        LocalDate startDate = LocalDate.of(2015, 2, 15);
        LocalDate endDate = LocalDate.of(2017, 1, 21);

        Period period = Period.between(startDate, endDate);

        assertFalse(period.isNegative());
        assertEquals(56, period.plusDays(50).getDays());
        assertEquals(9, period.minusMonths(2).getMonths());

        Period fromUnits = Period.of(3, 10, 10);
        Period fromDays = Period.ofDays(50);
        Period fromMonths = Period.ofMonths(5);
        Period fromYears = Period.ofYears(10);
        Period fromWeeks = Period.ofWeeks(40);

        assertEquals(280, fromWeeks.getDays());
        assertEquals(0, fromWeeks.getMonths());
        assertEquals(0, fromWeeks.getYears());

        assertEquals(50, fromDays.getDays());
        assertEquals(0, fromDays.getMonths());
        assertEquals(0, fromDays.getYears());

        assertEquals(0, fromMonths.getDays());
        assertEquals(5, fromMonths.getMonths());
        assertEquals(0, fromMonths.getYears());

        assertEquals(0, fromYears.getDays());
        assertEquals(0, fromYears.getMonths());
        assertEquals(10, fromYears.getYears());

        assertEquals(10, fromUnits.getDays());
        assertEquals(10, fromUnits.getMonths());
        assertEquals(3, fromUnits.getYears());

        Period fromCharYears = Period.parse("P2Y");
        assertEquals(2, fromCharYears.getYears());
        Period fromCharUnits = Period.parse("P2Y3M5D");
        assertEquals(5, fromCharUnits.getDays());
    }
}
