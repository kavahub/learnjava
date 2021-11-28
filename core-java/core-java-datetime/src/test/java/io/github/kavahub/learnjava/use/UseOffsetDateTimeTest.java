package io.github.kavahub.learnjava.use;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.use.UseOffsetDateTime.*;

/**
 * 
 * {@link UseOffsetDateTime} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class UseOffsetDateTimeTest {
    @Test
    public void givenAZoneOffSetAndLocalDateTime_whenCombing_thenValidResult() {
        ZoneOffset offset = ZoneOffset.of("+02:00");
        LocalDateTime localDateTime = LocalDateTime.of(2015, Month.FEBRUARY, 20, 6, 30);

        OffsetDateTime result = offsetOfLocalDateTimeAndOffset(localDateTime, offset);

        assertThat(result.toString()).isEqualTo("2015-02-20T06:30+02:00");
    } 
}
