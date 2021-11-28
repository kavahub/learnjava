package io.github.kavahub.learnjava.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import lombok.experimental.UtilityClass;

/**
 * {@link Date} 日期比较
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
@UtilityClass
public class LegacyDateComparer {
    
    /**
     * 随机生成之间的日期
     * 
     * @param date
     * @param dateToCompare
     * @return
     */
    public boolean isSameDay(Date date, Date dateToCompare) {
        return DateUtils.isSameDay(date, dateToCompare);
    }

    public boolean isSameHour(Date date, Date dateToCompare) {
        return DateUtils.truncatedEquals(date, dateToCompare, Calendar.HOUR);
    }   
}
