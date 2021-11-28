package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link TemporalAdjusters} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class TemporalAdjustersTest {
    @Test
    public void whenAdjust_thenNextSunday() {
        LocalDate localDate = LocalDate.of(2021, 10, 21);
        LocalDate nextSunday = localDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        String expected = "2021-10-24";

        assertEquals(expected, nextSunday.toString());
    }    
}
