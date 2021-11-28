package io.github.kavahub.learnjava;

import static java.util.concurrent.CompletableFuture.runAsync;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * {@link Exchanger} 示例
 *
 * <p>
 * {@link CompletableFuture} 任务是不能中断(interrupt)
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class ExchangerPipeLineCancelManualTest {
    private static final int BUFFER_SIZE = 100;
    private boolean running = true;

    @Test
    public void givenData_whenPassedThrough_thenCorrect() throws InterruptedException, ExecutionException {
        Exchanger<Queue<String>> readerExchanger = new Exchanger<>();
        Exchanger<Queue<String>> writerExchanger = new Exchanger<>();

        Runnable reader = () -> {
            Queue<String> readerBuffer = new ConcurrentLinkedQueue<>();
            try {
                do {
                    while (readerBuffer.size() < BUFFER_SIZE) {
                        readerBuffer.add(UUID.randomUUID().toString());
                    }
                    log.info("[{}] 数据正在发送...", System.currentTimeMillis());
                    readerBuffer = readerExchanger.exchange(readerBuffer);

                    workTime(150);
                } while (running);
                log.info("reader canceled");
            } catch (InterruptedException e) {
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

                } while (running);
                log.info("processor canceled");
            } catch (InterruptedException e) {
                log.info("processor interrupted");
            }
        };

        Runnable writer = () -> {
            Queue<String> writerBuffer = new ConcurrentLinkedQueue<>();
            try {
                do {
                    writerBuffer = writerExchanger.exchange(writerBuffer);
                    log.info("[{}] 正在处理数据...", System.currentTimeMillis());
                    while (!writerBuffer.isEmpty()) {
                        writerBuffer.poll();
                    }

                    workTime(150);
                } while (running);

                log.info("writer canceled");
            } catch (InterruptedException e) {
                log.info("writer interrupted");
            }

        };

        CompletableFuture<Void> all = CompletableFuture.allOf(runAsync(reader), runAsync(processor), runAsync(writer));
        all.whenComplete((a, t) -> {
            running = false;
            log.info("Stop");
        });

        TimeUnit.SECONDS.sleep(5);
        all.cancel(false);
        TimeUnit.SECONDS.sleep(1);
    }

    private void workTime(long worktime) {
        final long now = System.currentTimeMillis();
        while (System.currentTimeMillis() <= now + worktime) {

        }
    }
}
