package io.github.kavahub.learnjava.enhance;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

/**
 * {@link TemporalAdjuster} 是函数接口，类中有很多预定义的实现
 * 
 * <p>
 * 类中预定义实现列表：
 * <ul>
 * <li> dayOfWeekInMonth()   : 一周中的某一天，例如，三月中第二个星期二 </li>
 * <li> firstDayOfMonth()    : 当前月的第一天 </li>
 * <li> firstDayOfNextMonth(): 下一个月的第一天 </li>
 * <li> firstDayOfNextYear() : 下一年的第一天 </li>
 * <li> firstDayOfYear()     : 当年的第一天 </li>
 * <li> lastDayOfMonth()     : 当月的最后一天 </li>
 * <li> nextOrSame()         : 下一次或当天发生的一周中的某天 </li>
 * </ul>
 */
public class CustomTemporalAdjuster implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        switch (DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK))) {
        case FRIDAY:
            return temporal.plus(3, ChronoUnit.DAYS);
        case SATURDAY:
            return temporal.plus(2, ChronoUnit.DAYS);
        default:
            return temporal.plus(1, ChronoUnit.DAYS);
        }
    }
    
}
