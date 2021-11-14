package io.github.kavahub.learnjava;

import java.time.LocalDate;
import java.time.Period;

public class UsePeriod {
    LocalDate modifyDates(LocalDate localDate, Period period) {
        return localDate.plus(period);
    }

    Period getDifferenceBetweenDates(LocalDate localDate1, LocalDate localDate2) {
        return Period.between(localDate1, localDate2);
    }    
}
