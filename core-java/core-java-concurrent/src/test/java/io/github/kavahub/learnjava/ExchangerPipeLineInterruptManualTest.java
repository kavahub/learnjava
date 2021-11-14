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

@Slf4j
public class ExchangerPipeLineInterruptManualTest {
    private static final int BUFFER_SIZE = 100;

    @Test
    public void givenData_whenPassedThrough_thenCorrect() throws InterruptedException, ExecutionException {
        Exchanger<Queue<String>> readerExchanger = new Exchanger<>();
        Exchanger<Queue<String>> writerExchanger = new Exchanger<>();

        Runnable reader = () -> {
                // ConcurrentLinkedQueue是线程安全的队列，它适用于“高并发”的场景。它是一个基于链接节点的无界线程安全队列，按照
                // FIFO（先进先出）原则对元素进行排序。队列元素中不可以放置null元素
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

        ExecutorService es = Executors.newFixedThreadPool(3);
        es.submit(reader);
        es.submit(processor);
        es.submit(writer);
        TimeUnit.SECONDS.sleep(5);

        es.shutdownNow();

        TimeUnit.SECONDS.sleep(5);

    }


}
