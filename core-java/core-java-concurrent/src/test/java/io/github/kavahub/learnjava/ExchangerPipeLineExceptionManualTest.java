package io.github.kavahub.learnjava;

import static java.util.concurrent.CompletableFuture.runAsync;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 注意：添加log输出后，需要添加sleep，否则，日志输出太快，造成IDE无响应。
 */
@Slf4j
public class ExchangerPipeLineExceptionManualTest {
    private static final int BUFFER_SIZE = 100;

    @Test
    public void givenData_whenPassedThrough_thenCorrect() throws InterruptedException, ExecutionException {
        Exchanger<Queue<String>> readerExchanger = new Exchanger<>();
        Exchanger<Queue<String>> writerExchanger = new Exchanger<>();

        Runnable reader = () -> {
            Queue<String> readerBuffer = new ConcurrentLinkedQueue<>();
            int no = 1;
            try {
                do {
                    while (readerBuffer.size() < BUFFER_SIZE) {
                        readerBuffer.add(UUID.randomUUID().toString());
                    }
                    log.info("第 {} 次，共 {} 条 数据正在发送...", no, readerBuffer.size());
                    readerBuffer = readerExchanger.exchange(readerBuffer);

                    no++;
                    TimeUnit.MILLISECONDS.sleep(150);
                } while (true);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("reader interrupted");
            }
        };

        Runnable processor = () -> {
            Queue<String> processorBuffer = new ConcurrentLinkedQueue<>();
            Queue<String> writerBuffer = new ConcurrentLinkedQueue<>();
            try {
                do {
                    processorBuffer = readerExchanger.exchange(processorBuffer);
                    while (!processorBuffer.isEmpty()) {
                        writerBuffer.add(processorBuffer.poll());
                    }
                    writerBuffer = writerExchanger.exchange(writerBuffer);
                } while (true);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("processor interrupted");
            }
        };

        Runnable writer = () -> {
            Queue<String> writerBuffer = new ConcurrentLinkedQueue<>();
            int no = 1;
            try {
                do {
                    writerBuffer = writerExchanger.exchange(writerBuffer);
                    log.info("第 {} 次，共 {} 条 数据正在处理...", no, writerBuffer.size());
                    while (!writerBuffer.isEmpty()) {
                        writerBuffer.poll();
                    }

                    no++;
                    TimeUnit.MILLISECONDS.sleep(250);
                } while (true);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("writer interrupted");
            }

        };

        CompletableFuture<Void> allFuture = waitForAllButAbortOnFirstException(runAsync(reader), runAsync(processor), runAsync(writer));
        
        // 5秒后停止任务
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                allFuture.completeExceptionally(new RuntimeException("手工停止任务"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        allFuture.get();
    }

    /**
     * 当其中有有一个异常时，任务全部停止
     * @param futures
     * @return
     */
    public CompletableFuture<Void> waitForAllButAbortOnFirstException(CompletableFuture<?>... futures) {
        CompletableFuture<Void> allWithFailFast = CompletableFuture.allOf(futures);
        Stream.of(futures).forEach(f -> f.exceptionally(e -> {
            allWithFailFast.completeExceptionally(e);
            return null;
        }));

        return allWithFailFast;
    }
}
