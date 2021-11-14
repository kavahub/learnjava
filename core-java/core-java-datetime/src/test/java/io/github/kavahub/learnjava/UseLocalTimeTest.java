package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class UseLocalTimeTest {
    private UseLocalTime useLocalTime = new UseLocalTime();

    @Test
    public void givenValues_whenUsingFactoryOf_thenLocalTime() {
        assertEquals("07:07:07", useLocalTime.getLocalTimeUsingFactoryOfMethod(7, 7, 7)
            .toString());
    }

    @Test
    public void givenValues_whenUsingFactoryOfWithoutSeconds_thenLocalTime() {
        assertEquals("07:07", useLocalTime.getLocalTimeUsingFactoryOfMethod(7, 7)
            .toString());
    }

    @Test
    public void givenString_whenUsingParse_thenLocalTime() {
        assertEquals("06:30", useLocalTime.getLocalTimeUsingParseMethod("06:30")
            .toString());
    }

    @Test
    public void givenTime_whenAddHour_thenLocalTime() {
        assertEquals("07:30", useLocalTime.addAnHour(LocalTime.of(6, 30))
            .toString());
    }

    @Test
    public void getHourFromLocalTime() {
        assertEquals(1, useLocalTime.getHourFromLocalTime(LocalTime.of(1, 1)));
    }

    @Test
    public void getLocalTimeWithMinuteSetToValue() {
        assertEquals(LocalTime.of(10, 20), useLocalTime.getLocalTimeWithMinuteSetToValue(LocalTime.of(10, 10), 20));
    }    
}
