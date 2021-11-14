package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import static java.util.concurrent.CompletableFuture.runAsync;

/**
 * Exchanger 是 JDK 1.5 开始提供的一个用于两个工作线程之间交换数据的封装工具类，简单说就是一个线程在完成一定的事务后想与另一个线程交换数据，
 * 则第一个先拿出数据的线程会一直等待第二个线程，直到第二个线程拿着数据到来时才能彼此交换对应数据。
 */
public class ExchangerTest {
    @Test
    public void givenThreads_whenMessageExchanged_thenCorrect() {
        Exchanger<String> exchanger = new Exchanger<>();

        Runnable taskA = () -> {
            try {
                String message = exchanger.exchange("from A");
                assertEquals("from B", message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };

        Runnable taskB = () -> {
            try {
                String message = exchanger.exchange("from B");
                assertEquals("from A", message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };

        CompletableFuture.allOf(runAsync(taskA), runAsync(taskB)).join();
    }

    @Test
    public void givenThread_WhenExchangedMessage_thenCorrect() throws InterruptedException, ExecutionException {
        Exchanger<String> exchanger = new Exchanger<>();

        Runnable runner = () -> {
            try {
                String message = exchanger.exchange("from runner");
                assertEquals("to runner", message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };

        CompletableFuture<Void> result = CompletableFuture.runAsync(runner);
        String msg = exchanger.exchange("to runner");
        assertEquals("from runner", msg);
        result.join();
    }

}
