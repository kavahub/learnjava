package io.github.kavahub.learnjava.use;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import lombok.experimental.UtilityClass;

/**
 * 
 * {@link Date} 转换成 {@link LocalDateTime}
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class UseToInstant {
    public LocalDateTime convertDateToLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public LocalDateTime convertDateToLocalDate(Calendar calendar) {
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }   
}
