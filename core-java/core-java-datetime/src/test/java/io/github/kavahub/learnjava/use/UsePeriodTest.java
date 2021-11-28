package io.github.kavahub.learnjava.use;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.use.UsePeriod.*;

/**
 * 
 * {@link UsePeriod} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class UsePeriodTest {
    @Test
    public void givenPeriodAndLocalDate_thenCalculateModifiedDate() {
        Period period = Period.ofDays(1);
        LocalDate localDate = LocalDate.parse("2007-05-10");
        assertEquals("2007-05-11", modifyDates(localDate, period).toString());
    }

    @Test
    public void givenDates_thenGetPeriod() {
        LocalDate localDate1 = LocalDate.parse("2007-05-10");
        LocalDate localDate2 = LocalDate.parse("2007-05-15");

        assertEquals(Period.ofDays(5), getDifferenceBetweenDates(localDate1, localDate2));
    }    
}
