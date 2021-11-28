package io.github.kavahub.learnjava.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link SequenceGenerator} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SequenceGeneratorTest {
    @Test
    // This test verifies the race condition use case, it may pass or fail based on execution environment
    // Uncomment @Test to run it
    @Disabled("可能运行失败")
    public void givenUnsafeSequenceGenerator_whenRaceCondition_thenUnexpectedBehavior() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGenerator(), count);
        assertNotEquals(count, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingSynchronizedMethod_whenRaceCondition_thenSuccess() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingSynchronizedMethod(), count);
        assertEquals(count, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingSynchronizedBlock_whenRaceCondition_thenSuccess() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingSynchronizedBlock(), count);
        assertEquals(count, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingReentrantLock_whenRaceCondition_thenSuccess() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingReentrantLock(), count);
        assertEquals(count, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingSemaphore_whenRaceCondition_thenSuccess() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingSemaphore(), count);
        assertEquals(count, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingMonitor_whenRaceCondition_thenSuccess() throws Exception {
        int count = 1000;
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingMonitor(), count);
        assertEquals(count, uniqueSequences.size());
    }

    private Set<Integer> getUniqueSequences(SequenceGenerator generator, int count) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Set<Integer> uniqueSequences = new LinkedHashSet<>();
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            futures.add(executor.submit(generator::getNextSequence));
        }

        for (Future<Integer> future : futures) {
            uniqueSequences.add(future.get());
        }

        executor.awaitTermination(1, TimeUnit.SECONDS);
        executor.shutdown();

        return uniqueSequences;
    }
}
