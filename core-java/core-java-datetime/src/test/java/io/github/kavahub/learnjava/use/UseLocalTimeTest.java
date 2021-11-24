package io.github.kavahub.learnjava.use;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.use.UseLocalTime.*;

public class UseLocalTimeTest {
    @Test
    public void givenValues_whenUsingFactoryOf_thenLocalTime() {
        assertEquals("07:07:07", getLocalTimeUsingFactoryOfMethod(7, 7, 7)
            .toString());
    }

    @Test
    public void givenValues_whenUsingFactoryOfWithoutSeconds_thenLocalTime() {
        assertEquals("07:07", getLocalTimeUsingFactoryOfMethod(7, 7)
            .toString());
    }

    @Test
    public void givenString_whenUsingParse_thenLocalTime() {
        assertEquals("06:30", getLocalTimeUsingParseMethod("06:30")
            .toString());
    }

    @Test
    public void givenTime_whenAddHour_thenLocalTime() {
        assertEquals("07:30", addAnHour(LocalTime.of(6, 30))
            .toString());
    }

    @Test
    public void givenTime_whenGetHour_thenLocalTime() {
        assertEquals(1, getHourFromLocalTime(LocalTime.of(1, 1)));
    }

    @Test
    public void givenTime_whenGetLocalTime_thenLocalTime() {
        assertEquals(LocalTime.of(10, 20), getLocalTimeWithMinuteSetToValue(LocalTime.of(10, 10), 20));
    }    
}
