package io.github.kavahub.learnjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.experimental.UtilityClass;

/**
 * 
 * {@link ExecutorService} 工具
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class ExecutorServiceHelper {
    /**
     * 关闭线程池，默认等待5秒
     * 
     * @param executor
     */
    public void close(ExecutorService executor) {
        close(executor, 5, TimeUnit.SECONDS);
    }

    /**
     * 关闭线程池
     * 
     * @param executor
     * @param terminationTimeout
     * @param unit
     */
    public void close(ExecutorService executor, long terminationTimeout, TimeUnit unit) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(terminationTimeout, unit)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
