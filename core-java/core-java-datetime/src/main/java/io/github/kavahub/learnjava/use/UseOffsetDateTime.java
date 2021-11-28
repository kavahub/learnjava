package io.github.kavahub.learnjava.use;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import lombok.experimental.UtilityClass;

/**
 * 
 * {@link OffsetDateTime} 使用
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class UseOffsetDateTime {
    public OffsetDateTime offsetOfLocalDateTimeAndOffset(LocalDateTime localDateTime, ZoneOffset offset) {
        return OffsetDateTime.of(localDateTime, offset);
    }   
}
