package io.github.kavahub.learnjava;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LegacyDateComparisonUtils {
    public boolean isSameDay(Date date, Date dateToCompare) {
        return DateUtils.isSameDay(date, dateToCompare);
    }

    public boolean isSameHour(Date date, Date dateToCompare) {
        return DateUtils.truncatedEquals(date, dateToCompare, Calendar.HOUR);
    }   
}
