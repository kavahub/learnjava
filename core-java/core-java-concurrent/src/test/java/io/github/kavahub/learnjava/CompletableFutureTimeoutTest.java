package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;

/**
 * {@link CompletableFuture#orTimeout(long, TimeUnit)} 调用上述方法之后，如果未在指定的超时内完成，
 * 将来会抛出 {@code ExecutionException} 异常。
 * 
 * <p>
 * {@code CompletableFuture#completeOnTimeout(Object, long, TimeUnit)}达到超时后返回默认值
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
public class CompletableFutureTimeoutTest {
    @Test
    public void testDelay() throws Exception {
        Object input = new Object();
        CompletableFuture<Object> future = new CompletableFuture<>();
        future.completeAsync(() -> input, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));

        Thread.sleep(100);

        assertFalse(future.isDone());

        Thread.sleep(1000);
        assertTrue(future.isDone());
        assertSame(input, future.get());
    }

    @Test
    public void testTimeoutTriggered() throws Exception {
        CompletableFuture<Object> future = new CompletableFuture<>();
        future.orTimeout(1, TimeUnit.SECONDS);

        Thread.sleep(1100);

        assertTrue(future.isDone());

        try {
            future.get();
        } catch (ExecutionException e) {
            assertTrue(e.getCause() instanceof TimeoutException);
        }
    }

    @Test
    public void testTimeoutNotTriggered() throws Exception {
        Object input = new Object();
        CompletableFuture<Object> future = new CompletableFuture<>();

        future.orTimeout(1, TimeUnit.SECONDS);

        Thread.sleep(100);

        future.complete(input);

        Thread.sleep(1000);

        assertTrue(future.isDone());
        assertSame(input, future.get());
    }

    @Test
    public void completeOnTimeout() throws Exception {
        Object input = new Object();
        CompletableFuture<Object> future = new CompletableFuture<>();
        future.completeOnTimeout(input, 1, TimeUnit.SECONDS);

        Thread.sleep(1100);

        assertTrue(future.isDone());
        assertSame(input, future.get());
    }
}
