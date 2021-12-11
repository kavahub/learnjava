package io.github.kavahub.learnjava;

import lombok.extern.slf4j.Slf4j;

/**
 * 秒表，计算开始到结束直接的毫秒数
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class StopWatch {
    static ThreadLocal<Long> t = new ThreadLocal<Long>();

    public static void start() {
        t.set(System.currentTimeMillis());
    }

    public static void end() {
        final long elapseOfTime = System.currentTimeMillis() - t.get();
        log.info("{} elapse of time: {}", Thread.currentThread().getStackTrace()[2] , elapseOfTime);

        t.remove();          
    }

}
