package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link RejectedExecutionHandler} 示例
 *
 * <p>
 * 支持自定义策略。已支持的有：
 * <ul>
 * <li> AbortPolicy（默认）：直接抛出RejectedExecutionException异常阻止系统正常运行 </li>
 * <li> CallerRunsPolicy: 在任务被拒绝添加后，会在调用execute方法的的线程来执行被拒绝的任务 </li>
 * <li> DiscardPolicy: 拒绝任务的处理程序，以静默方式丢弃被拒绝的任务  </li>
 * <li> DiscardOldestPolicy: 抛弃进入队列最早(队首)的那个任务，然后尝试把这次拒绝的任务放入队列（队尾） </li>
 * </ul>
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ThreadPoolExecutorPolicyTest {
    private ThreadPoolExecutor executor;

    @AfterEach
    public void shutdownExecutor() {
        if (executor != null && !executor.isTerminated()) {
            executor.shutdownNow();
        }
    }

    @Test
    public void givenAbortPolicy_WhenSaturated_ThenShouldThrowRejectedExecutionException() {
        // 只能运行一个线程，当任务队列满时，执行定义策略
        // SynchronousQueue是一种阻塞队列，其中每个 put 必须等待一个 take，反之亦然
        executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), new AbortPolicy());
        executor.execute(() -> waitFor(250));

        assertThatThrownBy(() -> executor.execute(() -> System.out.println("Will be rejected")))
                .isInstanceOf(RejectedExecutionException.class);
    }

    @Test
    public void givenCallerRunsPolicy_WhenSaturated_ThenTheCallerThreadRunsTheTask() {
        executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>(),
                new CallerRunsPolicy());
        executor.execute(() -> waitFor(250));

        long startTime = System.currentTimeMillis();
        executor.execute(() -> waitFor(500));
        long blockedDuration = System.currentTimeMillis() - startTime;

        assertThat(blockedDuration).isGreaterThanOrEqualTo(500);
    }

    @Test
    public void givenDiscardPolicy_WhenSaturated_ThenExecutorDiscardsTheNewTask() throws InterruptedException {
        executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>(),
                new DiscardPolicy());
        executor.execute(() -> waitFor(100));

        BlockingQueue<String> queue = new LinkedBlockingDeque<>();
        executor.execute(() -> queue.offer("Result"));

        assertThat(queue.poll(200, TimeUnit.MILLISECONDS)).isNull();
    }

    @Test
    public void givenDiscardOldestPolicy_WhenSaturated_ThenExecutorDiscardsTheOldestTask() throws InterruptedException {
        executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2),
                new DiscardOldestPolicy());
        executor.execute(() -> waitFor(100));

        BlockingQueue<String> queue = new LinkedBlockingDeque<>();
        executor.execute(() -> queue.offer("Third"));
        executor.execute(() -> queue.offer("Second"));
        executor.execute(() -> queue.offer("First"));

        waitFor(150);
        assertThat(queue.poll(200, TimeUnit.MILLISECONDS)).isEqualTo("Second");
        assertThat(queue.poll(200, TimeUnit.MILLISECONDS)).isEqualTo("First");
        // 注意： drainTo方法结果是排序的
        // List<String> results = new ArrayList<>();
        // queue.drainTo(results);
        // assertThat(results).containsExactly("First", "Second");
    }

    @Test
    public void givenGrowPolicy_WhenSaturated_ThenExecutorIncreaseTheMaxPoolSize() throws InterruptedException {
        executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2),
                new GrowPolicy());
        executor.execute(() -> waitFor(100));

        BlockingQueue<String> queue = new LinkedBlockingDeque<>();
        executor.execute(() -> queue.offer("Third"));
        executor.execute(() -> queue.offer("Second"));
        executor.execute(() -> queue.offer("First"));

        waitFor(150);

        assertThat(queue.poll(200, TimeUnit.MILLISECONDS)).isEqualTo("First");
        assertThat(queue.poll(200, TimeUnit.MILLISECONDS)).isEqualTo("Third");
        assertThat(queue.poll(200, TimeUnit.MILLISECONDS)).isEqualTo("Second");
        // List<String> results = new ArrayList<>();
        // queue.drainTo(results);
        // assertThat(results).containsExactlyInAnyOrder("First", "Second", "Third");
    }

    @Test
    public void givenExecutorIsTerminated_WhenSubmittedNewTask_ThenTheSaturationPolicyApplies() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        executor.shutdownNow();

        assertThatThrownBy(() -> executor.execute(() -> {
        })).isInstanceOf(RejectedExecutionException.class);
    }

    @Test
    public void givenExecutorIsTerminating_WhenSubmittedNewTask_ThenTheSaturationPolicyApplies() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        executor.execute(() -> waitFor(100));
        executor.shutdown();

        assertThatThrownBy(() -> executor.execute(() -> {
        })).isInstanceOf(RejectedExecutionException.class);
    }

    /**
     * 自定义。扩充缓冲池，先运行阻塞任务
     */
    private static class GrowPolicy implements RejectedExecutionHandler {

        private final Lock lock = new ReentrantLock();

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            lock.lock();
            try {
                executor.setMaximumPoolSize(executor.getMaximumPoolSize() + 1);
            } finally {
                lock.unlock();
            }

            executor.submit(r);
        }
    }

    private void waitFor(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
