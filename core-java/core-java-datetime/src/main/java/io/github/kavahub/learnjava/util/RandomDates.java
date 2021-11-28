package io.github.kavahub.learnjava.util;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import lombok.experimental.UtilityClass;

/**
 * 
 * {@link Date} 随机生成
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class RandomDates {
    /**
     * 随机生成之间的日期
     * 
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public Date between(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom.current().nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }

    /**
     * 随机生成日期
     * 
     * @return
     */
    public Date timestamp() {
        return new Date(ThreadLocalRandom.current().nextInt() * 1000L);
    }  
}
