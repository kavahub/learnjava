package io.github.kavahub.learnjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import lombok.experimental.UtilityClass;

import static java.time.temporal.ChronoUnit.*;

/**
 * 
 * 日期时间比较器
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class LocalDateTimeComparer {
    public boolean isSameDay(LocalDateTime timestamp, LocalDate localDateToCompare) {
        return timestamp.toLocalDate().isEqual(localDateToCompare);
    }

    public boolean isSameDay(LocalDateTime timestamp, LocalDateTime timestampToCompare) {
        return timestamp.truncatedTo(DAYS).isEqual(timestampToCompare.truncatedTo(DAYS));
    }

    public boolean isSameHour(LocalDateTime timestamp, LocalDateTime timestampToCompare) {
        return timestamp.truncatedTo(HOURS).isEqual(timestampToCompare.truncatedTo(HOURS));
    }

    public boolean isSameMinute(LocalDateTime timestamp, LocalDateTime timestampToCompare) {
        return timestamp.truncatedTo(MINUTES).isEqual(timestampToCompare.truncatedTo(MINUTES));
    }

    public boolean isSameHour(ZonedDateTime zonedTimestamp, ZonedDateTime zonedTimestampToCompare) {
        return zonedTimestamp.truncatedTo(HOURS).isEqual(zonedTimestampToCompare.truncatedTo(HOURS));
    }

    public boolean isSameHour(ZonedDateTime zonedDateTime, LocalDateTime localDateTime, ZoneId zoneId) {
        return isSameHour(zonedDateTime, localDateTime.atZone(zoneId));
    }
}
