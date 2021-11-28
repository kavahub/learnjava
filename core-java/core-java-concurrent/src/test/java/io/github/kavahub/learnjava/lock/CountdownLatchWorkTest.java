package io.github.kavahub.learnjava.lock;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link CountDownLatch} 示例
 *
 * <p>
 * {@code CountDownLatch} 能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。使用一个计数器进行实现。
 * 计数器初始值为线程的数量。当每一个线程完成自己任务后，计数器的值就会减一。当计数器的值为0时，
 * 表示所有的线程都已经完成一些任务，然后在 {@code CountDownLatch} 上等待的线程就可以恢复执行接下来的任务
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CountdownLatchWorkTest {
        @Test
        public void whenParallelProcessing_thenMainThreadWillBlockUntilCompletion() throws InterruptedException {
                // Given
                List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
                CountDownLatch countDownLatch = new CountDownLatch(5);
                List<Thread> workers = Stream.generate(() -> new Thread(new Worker(outputScraper, countDownLatch)))
                                .limit(5)
                                .collect(Collectors.toList());

                // When
                workers.forEach(Thread::start);
                countDownLatch.await(); // Block until workers finish
                outputScraper.add("Latch released");

                // Then
                assertThat(outputScraper).containsExactly("Counted down", "Counted down", "Counted down",
                                "Counted down",
                                "Counted down", "Latch released");
        }

        @Test
        public void whenFailingToParallelProcess_thenMainThreadShouldTimeout() throws InterruptedException {
                // Given
                List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
                CountDownLatch countDownLatch = new CountDownLatch(5);
                List<Thread> workers = Stream
                                .generate(() -> new Thread(new WorkerBroken(outputScraper, countDownLatch)))
                                .limit(5).collect(Collectors.toList());

                // When
                workers.forEach(Thread::start);
                final boolean result = countDownLatch.await(3L, TimeUnit.SECONDS);

                // Then
                assertThat(result).isFalse();
        }

        @Test
        public void whenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime() throws InterruptedException {
                // Given
                List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
                CountDownLatch readyThreadCounter = new CountDownLatch(5);
                CountDownLatch callingThreadBlocker = new CountDownLatch(1);
                CountDownLatch completedThreadCounter = new CountDownLatch(5);
                List<Thread> workers = Stream.generate(() -> new Thread(
                                new WorkerWaiting(outputScraper, readyThreadCounter, callingThreadBlocker,
                                                completedThreadCounter)))
                                .limit(5).collect(Collectors.toList());

                // When
                workers.forEach(Thread::start);
                readyThreadCounter.await(); // Block until workers start
                outputScraper.add("Workers ready");
                callingThreadBlocker.countDown(); // Start workers
                completedThreadCounter.await(); // Block until workers finish
                outputScraper.add("Workers complete");

                // Then
                assertThat(outputScraper).containsExactly("Workers ready", "Counted down", "Counted down",
                                "Counted down",
                                "Counted down", "Counted down", "Workers complete");
        }

}
