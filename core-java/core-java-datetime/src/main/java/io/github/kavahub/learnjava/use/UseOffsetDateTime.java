package io.github.kavahub.learnjava.use;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UseOffsetDateTime {
    public OffsetDateTime offsetOfLocalDateTimeAndOffset(LocalDateTime localDateTime, ZoneOffset offset) {
        return OffsetDateTime.of(localDateTime, offset);
    }   
}
