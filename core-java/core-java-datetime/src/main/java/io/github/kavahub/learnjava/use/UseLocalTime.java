package io.github.kavahub.learnjava.use;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UseLocalTime {
    LocalTime getLocalTimeUsingFactoryOfMethod(int hour, int min, int seconds) {
        return LocalTime.of(hour, min, seconds);
    }

    LocalTime getLocalTimeUsingFactoryOfMethod(int hour, int min) {
        return LocalTime.of(hour, min);
    }

    LocalTime getLocalTimeUsingParseMethod(String timeRepresentation) {
        return LocalTime.parse(timeRepresentation);
    }

    LocalTime getLocalTimeFromClock() {
        return LocalTime.now();
    }

    LocalTime addAnHour(LocalTime localTime) {
        return localTime.plus(1, ChronoUnit.HOURS);
    }

    int getHourFromLocalTime(LocalTime localTime) {
        return localTime.getHour();
    }

    LocalTime getLocalTimeWithMinuteSetToValue(LocalTime localTime, int minute) {
        return localTime.withMinute(minute);
    }    
}
