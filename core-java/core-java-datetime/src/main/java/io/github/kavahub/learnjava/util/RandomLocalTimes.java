package io.github.kavahub.learnjava.util;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

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
