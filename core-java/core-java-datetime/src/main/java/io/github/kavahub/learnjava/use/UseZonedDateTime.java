package io.github.kavahub.learnjava.use;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

import lombok.experimental.UtilityClass;

/**
 * 
 * {@link ZonedDateTime} 使用
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class UseZonedDateTime {
    ZonedDateTime getZonedDateTime(LocalDateTime localDateTime, ZoneId zoneId) {
        return ZonedDateTime.of(localDateTime, zoneId);
    }

    ZonedDateTime getZonedDateTimeUsingParseMethod(String parsableString) {
        return ZonedDateTime.parse(parsableString);
    }

    ZonedDateTime getStartOfDay(LocalDate localDate, ZoneId zone) {
        ZonedDateTime startOfDay = localDate.atStartOfDay()
            .atZone(zone);
        return startOfDay;
    }

    ZonedDateTime getStartOfDayShorthand(LocalDate localDate, ZoneId zone) {
        ZonedDateTime startOfDay = localDate.atStartOfDay(zone);
        return startOfDay;
    }

    ZonedDateTime getStartOfDayFromZonedDateTime(ZonedDateTime zonedDateTime) {
        ZonedDateTime startOfDay = zonedDateTime.toLocalDateTime()
            .toLocalDate()
            .atStartOfDay(zonedDateTime.getZone());
        return startOfDay;
    }

    ZonedDateTime getStartOfDayAtMinTime(ZonedDateTime zonedDateTime) {
        ZonedDateTime startOfDay = zonedDateTime.with(ChronoField.HOUR_OF_DAY, 0);
        return startOfDay;
    }

    ZonedDateTime getStartOfDayAtMidnightTime(ZonedDateTime zonedDateTime) {
        ZonedDateTime startOfDay = zonedDateTime.with(ChronoField.NANO_OF_DAY, 0);
        return startOfDay;
    }    
}
