package io.github.kavahub.learnjava.use;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.use.UseZonedDateTime.*;

/**
 * 
 * {@link UseZonedDateTime} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class UseZonedDateTimeTest {
    @Test
    public void givenZoneId_thenZonedDateTime() {
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime zonedDatetime = getZonedDateTime(LocalDateTime.parse("2016-05-20T06:30"), zoneId);
        assertEquals(zoneId, ZoneId.from(zonedDatetime));
    }

    @Test
    public void whenRequestingZones_thenAtLeastOneIsReturned() {
        Set<String> allZoneIds = ZoneId.getAvailableZoneIds();

        assertThat(allZoneIds.size()).isGreaterThan(1);
    }

    @Test
    public void givenLocalDateOrZoned_whenSettingStartOfDay_thenReturnMidnightInAllCases() {
        LocalDate given = LocalDate.parse("2018-06-23");
        ZoneId zone = ZoneId.of("Europe/Paris");
        ZonedDateTime zonedGiven = ZonedDateTime.of(given, LocalTime.NOON, zone);

        ZonedDateTime startOfOfDayWithMethod = getStartOfDay(given, zone);
        ZonedDateTime startOfOfDayWithShorthandMethod = getStartOfDayShorthand(given, zone);
        ZonedDateTime startOfOfDayFromZonedDateTime = getStartOfDayFromZonedDateTime(zonedGiven);
        ZonedDateTime startOfOfDayAtMinTime = getStartOfDayAtMinTime(zonedGiven);
        ZonedDateTime startOfOfDayAtMidnightTime = getStartOfDayAtMidnightTime(zonedGiven);

        assertThat(startOfOfDayWithMethod).isEqualTo(startOfOfDayWithShorthandMethod)
            .isEqualTo(startOfOfDayFromZonedDateTime)
            .isEqualTo(startOfOfDayAtMinTime)
            .isEqualTo(startOfOfDayAtMidnightTime);
        assertThat(startOfOfDayWithMethod.toLocalTime()).isEqualTo(LocalTime.MIDNIGHT);
        assertThat(startOfOfDayWithMethod.toLocalTime()
            .toString()).isEqualTo("00:00");
    }

    /**
     * @see <a href="https://infogalactic.com/info/List_of_tz_database_time_zones">Infogalactic</a>
     */
    @Test
    public void givenAStringWithTimeZone_whenParsing_thenEqualsExpected() {
        // UTC offset = +01:00,  UTC DST offset = +02:00,

        //ZonedDateTime resultFromString = zonedDateTime.getZonedDateTimeUsingParseMethod("2015-05-03T10:15:30+01:00[Europe/Paris]");
        ZonedDateTime resultFromString = getZonedDateTimeUsingParseMethod("2015-05-03T10:15:30+02:00[Europe/Paris]");
        ZonedDateTime resultFromLocalDateTime = ZonedDateTime.of(2015, 5, 3, 10, 15, 30, 0, ZoneId.of("Europe/Paris"));

        assertThat(resultFromString.getZone()).isEqualTo(ZoneId.of("Europe/Paris"));
        assertThat(resultFromLocalDateTime.getZone()).isEqualTo(ZoneId.of("Europe/Paris"));

        assertThat(resultFromString).isEqualTo(resultFromLocalDateTime);
    } 
    
    @Test
    public void givenAStringWithTimeZone_whenParsingByShanghai_thenEqualsExpected() {
        ZonedDateTime resultFromString = getZonedDateTimeUsingParseMethod("2015-05-03T10:15:30+08:00[Asia/Shanghai]");
        ZonedDateTime resultFromLocalDateTime = ZonedDateTime.of(2015, 5, 3, 10, 15, 30, 0, ZoneId.of("Asia/Shanghai"));

        assertThat(resultFromString.getZone()).isEqualTo(ZoneId.of("Asia/Shanghai"));
        assertThat(resultFromLocalDateTime.getZone()).isEqualTo(ZoneId.of("Asia/Shanghai"));

        assertThat(resultFromString).isEqualTo(resultFromLocalDateTime);
    }  
}
