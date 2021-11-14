package io.github.kavahub.learnjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExecutorServiceHelper {
    public void close(ExecutorService executor) {
        close(executor, 5000, TimeUnit.MILLISECONDS);
    }

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
