package io.github.kavahub.learnjava.util;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * {@link LocalTime} 随机生成
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class RandomLocalTimes {
    /**
     * 随机生成之间的时间
     * @param startTime
     * @param endTime
     * @return
     */
    public static LocalTime between(LocalTime startTime, LocalTime endTime) {
        int startSeconds = startTime.toSecondOfDay();
        int endSeconds = endTime.toSecondOfDay();
        int randomTime = ThreadLocalRandom.current().nextInt(startSeconds, endSeconds);

        return LocalTime.ofSecondOfDay(randomTime);
    }

    /**
     * 随机时间
     * 
     * @return
     */
    public static LocalTime time() {
        return between(LocalTime.MIN, LocalTime.MAX);
    }
}
