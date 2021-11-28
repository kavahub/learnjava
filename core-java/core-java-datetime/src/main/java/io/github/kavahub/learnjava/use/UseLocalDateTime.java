package io.github.kavahub.learnjava.use;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

import lombok.experimental.UtilityClass;

/**
 * 
 * {@link LocalDateTime} 使用
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class UseLocalDateTime {
    public LocalDateTime getLocalDateTimeUsingParseMethod(String representation) {
        return LocalDateTime.parse(representation);
    }

    LocalDateTime getEndOfDayFromLocalDateTimeDirectly(LocalDateTime localDateTime) {
        LocalDateTime endOfDate = localDateTime.with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay());
        return endOfDate;
    }

    LocalDateTime getEndOfDayFromLocalDateTime(LocalDateTime localDateTime) {
        LocalDateTime endOfDate = localDateTime.toLocalDate()
            .atTime(LocalTime.MAX);
        return endOfDate;
    }

    /**
     * 纪元秒
     * 
     * @param epochSecond
     * @param zoneOffset
     * @return
     */
    LocalDateTime ofEpochSecond(int epochSecond, ZoneOffset zoneOffset) {
        return LocalDateTime.ofEpochSecond(epochSecond, 0, zoneOffset);
    }   
}
