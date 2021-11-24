package io.github.kavahub.learnjava.use;

import java.time.LocalDate;
import java.time.Period;

import lombok.experimental.UtilityClass;

/**
 * {@link Period} 表示是两个日期之间的差
 */
@UtilityClass
public class UsePeriod {
    LocalDate modifyDates(LocalDate localDate, Period period) {
        return localDate.plus(period);
    }

    Period getDifferenceBetweenDates(LocalDate localDate1, LocalDate localDate2) {
        return Period.between(localDate1, localDate2);
    }    
}
