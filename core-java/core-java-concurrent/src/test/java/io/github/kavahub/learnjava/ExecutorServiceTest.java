package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link ExecutorService} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ExecutorServiceTest {
    private ExecutorService executorService;

    @BeforeEach
    public void setUp() {
        executorService = Executors.newSingleThreadExecutor();
    }

    @AfterEach
    public void clearUp() {
        executorService.shutdown();
    }

    @Test
    @Disabled("无论发生什么情况，都不要打扰工作的人；建议不要埋头干工作，经常抬头看下时钟，是否已经到下班时间")
    public void givenNotStopTask_whenShutdowNow() throws InterruptedException {
        executorService.submit(() -> {
            final long worktime = 5 * 1000;
            final long now = System.currentTimeMillis();
            while(System.currentTimeMillis() <= now + worktime) {
                //System.out.println(System.currentTimeMillis());
            }

            System.out.println("我自己停止了");
        });

        TimeUnit.SECONDS.sleep(1); // 运行任务
        assertThat(executorService.isShutdown()).isFalse();
        assertThat(executorService.isTerminated()).isFalse();

        executorService.awaitTermination(800, TimeUnit.MILLISECONDS);
        assertThat(executorService.isShutdown()).isFalse();
        assertThat(executorService.isTerminated()).isFalse();

        executorService.shutdown();
        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isFalse();

        executorService.shutdownNow();
        assertThat(executorService.isTerminated()).isFalse();

        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void givenAnExecutorService_WhenMoreTasksSubmitted_ThenAdditionalTasksWait() throws InterruptedException {

        // Given
        int noOfThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(noOfThreads);

        Runnable runnableTask = () -> {
            try {
                System.out.println(Thread.currentThread().getName());
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // When
        IntStream.rangeClosed(1, 10).forEach(i -> executorService.submit(runnableTask));

        // Then
        assertThat(((ThreadPoolExecutor) executorService).getQueue().size()).isEqualTo(5);
    }

    @Test
    public void givenExceptionTask_whenGet_ThenCallableThrowsIt() throws ExecutionException, InterruptedException {
        Future<Integer> future= executorService.submit(ExecutorServiceTest::getIntegerException);
        // 调用get方法时，才会运行callback
        assertThrows(ExecutionException.class, () -> future.get().intValue());
    }


    @Test
    public void givenExceptionTask_whenSleep_ThenDone() throws InterruptedException{
        Future<Integer> future= executorService.submit(ExecutorServiceTest::getIntegerException);
        assertEquals(false,future.isDone());

        TimeUnit.MILLISECONDS.sleep(1);
        assertEquals(true,future.isDone());
    }
    
    @Test
    public void givenFastestTask_ThenCalRunTime(){
        Future<?> future= executorService.submit(() -> {});

        long end = 0;
        int loop = 1;
        final long start = System.nanoTime();
        while(!future.isDone()) {
            end = System.nanoTime();
            loop++;
        }

        System.out.println(end - start);
        System.out.println(loop);
    }

    private static Integer getIntegerException() {
        throw new RuntimeException();
    }
}
