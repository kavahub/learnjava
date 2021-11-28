package io.github.kavahub.learnjava.enhance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link ReadWriteLockHashMap} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ReadWriteLockHashMapTest {
    @Test
    public void whenWriting_ThenNoReading() throws InterruptedException {
        ReadWriteLockHashMap object = new ReadWriteLockHashMap();
        final int threadCount = 3;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);

        executeWriterThreads(object, threadCount, service);
        TimeUnit.MILLISECONDS.sleep(10);
        
        assertEquals(object.isReadLockAvailable(), false);
        assertEquals(object.isWriteLockAvailable(), false);

        service.shutdown();
    }

    @Test
    public void whenReading_ThenMultipleReadingAllowed() {
        ReadWriteLockHashMap object = new ReadWriteLockHashMap();
        final int threadCount = 5;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);

        executeReaderThreads(object, threadCount, service);

        assertEquals(object.isReadLockAvailable(), true);
        assertEquals(object.isWriteLockAvailable(), false);

        service.shutdown();
    }

    private void executeWriterThreads(ReadWriteLockHashMap object, int threadCount, ExecutorService service) {
        for (int i = 0; i < threadCount; i++) {
            service.execute(() -> {
                try {
                    object.put("key" + threadCount, "value" + threadCount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void executeReaderThreads(ReadWriteLockHashMap object, int threadCount, ExecutorService service) {
        for (int i = 0; i < threadCount; i++)
            service.execute(() -> object.get("key" + threadCount));
    }
}
