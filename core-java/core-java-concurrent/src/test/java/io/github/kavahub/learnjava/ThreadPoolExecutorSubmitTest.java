package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ThreadPoolExecutorSubmitTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final static ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(1);

    private Runnable runnable = () -> {
        System.out.print("runnable");
    };

    private Callable<String> callable = () -> {
        System.out.print("callable");
        return "callable";
    };

    @AfterAll
    public static void clearUp() {
        WORKER_THREAD_POOL.shutdown();
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void givenCallable_whenSubmit() throws InterruptedException {
        WORKER_THREAD_POOL.submit(callable);

        final String output = outContent.toString();
        assertTrue(output.length() == 0);
    }

    @Test
    public void givenCallable_whenSubmit_thenSleep() throws InterruptedException {
        WORKER_THREAD_POOL.submit(callable);
        TimeUnit.SECONDS.sleep(1);

        final String output = outContent.toString();
        assertEquals("callable", output);
    }


    @Test
    public void givenCallable_whenSubmit_thenGet() throws InterruptedException, ExecutionException {
        Future<String> future = WORKER_THREAD_POOL.submit(callable);
        future.get();

        final String output = outContent.toString();
        assertEquals("callable", output);
    }

    @Test
    public void givenRunable_whenSubmit() throws InterruptedException {
        WORKER_THREAD_POOL.submit(runnable);

        final String output = outContent.toString();
        assertTrue(output.length() == 0);
    }


    @Test
    public void givenRunable_whenSubmit_thenSleep() throws InterruptedException {
        WORKER_THREAD_POOL.submit(runnable);
        TimeUnit.SECONDS.sleep(1);

        final String output = outContent.toString();
        assertEquals("runnable", output);
    }


    @Test
    public void givenRunable_whenSubmit_thenGet() throws InterruptedException, ExecutionException {
        Future<?> future = WORKER_THREAD_POOL.submit(runnable);
        future.get();

        final String output = outContent.toString();
        assertEquals("runnable", output);
    }
}
