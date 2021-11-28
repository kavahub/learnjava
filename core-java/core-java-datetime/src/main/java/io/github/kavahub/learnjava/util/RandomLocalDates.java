package io.github.kavahub.learnjava.util;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import lombok.experimental.UtilityClass;

/**
 * 
 * {@link LocalDate} 随机生成
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class RandomLocalDates {
    /**
     * 随机生成之间的日期
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    /**
     * 随机生成日期
     * @return
     */
    public LocalDate date() {
        int hundredYears = 100 * 365;
        return LocalDate.ofEpochDay(ThreadLocalRandom.current().nextInt(-hundredYears, hundredYears));
    }    
}
