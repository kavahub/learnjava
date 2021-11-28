package io.github.kavahub.learnjava;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * {@link Exchanger} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class ExchangerPipeLineManualTest {
    private static final int BUFFER_SIZE = 100;

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
                    } while (true);
      
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

                    } while (true);

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
                    } while (true);

                } catch (InterruptedException e) {
                    log.info("writer interrupted");
                }

        };

        ExecutorService es = Executors.newFixedThreadPool(3);
        es.submit(reader);
        es.submit(processor);
        es.submit(writer);
        TimeUnit.SECONDS.sleep(2);

        es.shutdown();
        es.awaitTermination(2, TimeUnit.SECONDS);
        es.shutdownNow();


        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 非阻塞等待，避免日志输出太快，导致IDE无响应
     * @param worktime
     */
    private void workTime(long worktime) {
        final long now = System.currentTimeMillis();
        while (System.currentTimeMillis() <= now + worktime) {

        }
    }
}
