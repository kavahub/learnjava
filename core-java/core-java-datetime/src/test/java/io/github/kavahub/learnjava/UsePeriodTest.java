package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class UsePeriodTest {
    UsePeriod usingPeriod = new UsePeriod();

    @Test
    public void givenPeriodAndLocalDate_thenCalculateModifiedDate() {
        Period period = Period.ofDays(1);
        LocalDate localDate = LocalDate.parse("2007-05-10");
        assertEquals("2007-05-11", usingPeriod.modifyDates(localDate, period).toString());
    }

    @Test
    public void givenDates_thenGetPeriod() {
        LocalDate localDate1 = LocalDate.parse("2007-05-10");
        LocalDate localDate2 = LocalDate.parse("2007-05-15");

        assertEquals(Period.ofDays(5), usingPeriod.getDifferenceBetweenDates(localDate1, localDate2));
    }    
}
