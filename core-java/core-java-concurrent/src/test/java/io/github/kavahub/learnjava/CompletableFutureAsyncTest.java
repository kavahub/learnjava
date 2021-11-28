package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * {@link CompletableFuture} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class CompletableFutureAsyncTest {
    private final static String NULL_STRING = null;

    @Test
    public void whenRunningCompletableFutureAsynchronously_thenGetMethodWaitsForResult()
            throws InterruptedException, ExecutionException {
        Future<String> completableFuture = calculateAsync();

        String result = completableFuture.get();
        assertEquals("Hello", result);
    }

    private Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return "world";
        });

        return completableFuture;
    }

    @Test
    public void whenRunningCompletableFutureWithResult_thenGetMethodReturnsImmediately()
            throws InterruptedException, ExecutionException {
        // completedFuture方法返回已经完成的CompletableFutures实例
        Future<String> completableFuture = CompletableFuture.completedFuture("Hello");

        String result = completableFuture.get();
        assertEquals("Hello", result);
    }

    @Test
    public void whenCancelingTheFuture_thenThrowsCancellationException()
            throws ExecutionException, InterruptedException {
        Future<String> future = calculateAsyncWithCancellation();
        assertThrows(CancellationException.class, () -> future.get());
    }

    private Future<String> calculateAsyncWithCancellation() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            // cancel方法取消任务的执行。参数指定是否立即中断任务执行，或者等等任务结束
            completableFuture.cancel(false);
            return null;
        });

        return completableFuture;
    }

    @Test
    public void whenCreatingCompletableFutureWithSupplyAsync_thenFutureReturnsValue()
            throws ExecutionException, InterruptedException {
        // 使用ForkJoinPool.commonPool()作为它的线程池执行异步代码，异步操作有返回值
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

        assertEquals("Hello", future.get());
    }

    @Test
    public void whenAddingThenAcceptToFuture_thenFunctionExecutesAfterComputationIsFinished()
            throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");

        // thenAccept 接收上一个任务的返回值作为参数，但是无返回值
        CompletableFuture<Void> future = completableFuture.thenAccept(s -> log.debug("Computation returned: " + s));

        future.get();

        // Output:
        // Computation returned: Hello
    }

    @Test
    public void whenAddingThenRunToFuture_thenFunctionExecutesAfterComputationIsFinished()
            throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");

        // thenRun只要前面的任务执行完成，它就执行，没有参数也没有返回值
        CompletableFuture<Void> future = completableFuture.thenRun(() -> log.debug("Computation finished."));

        future.get();

        // Output:
        // Computation finished.
    }

    @Test
    public void whenAddingThenApplyToFuture_thenFunctionExecutesAfterComputationIsFinished()
            throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        // thenApply 表示某个任务执行完成后执行的动作，即回调方法，会将该任务的执行结果即方法返回值作为入参传递到回调方法中，执行后返回值
        CompletableFuture<String> future = completableFuture.thenApply(s -> s + " World");

        assertEquals("Hello World", future.get());
    }

    @Test
    public void whenUsingThenCompose_thenFuturesExecuteSequentially() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                // thenCompose方法会在某个任务执行完成后，将该任务的执行结果作为方法入参然后执行指定的方法，该方法会返回一个新的CompletableFuture实例
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));

        assertEquals("Hello World", completableFuture.get());
    }

    @Test
    public void whenUsingThenCombine_thenWaitForExecutionOfBothFutures()
            throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                // thenCombine会将两个任务的执行结果作为方法入参传递到指定方法中，且该方法有返回值
                .thenCombine(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> s1 + s2);

        assertEquals("Hello World", completableFuture.get());
    }

    @Test
    public void whenUsingThenAcceptBoth_thenWaitForExecutionOfBothFutures()
            throws ExecutionException, InterruptedException {
        // thenAcceptBoth同样将两个任务的执行结果作为方法入参，但是无返回值(只消费，不生产)
        CompletableFuture.supplyAsync(() -> "Hello").thenAcceptBoth(CompletableFuture.supplyAsync(() -> " World"),
                (s1, s2) -> log.debug(s1 + s2));

        // Output:
        // Hello World
    }

    @Test
    public void whenFutureCombinedWithAllOfCompletes_thenAllFuturesAreDone()
            throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Beautiful");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "World");

        // allOf返回的CompletableFuture是多个任务都执行完成后才会执行。只要其中一个异常，则抛异常；都正常，则返回Void
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);

        // ...

        combinedFuture.get();

        assertTrue(future1.isDone());
        assertTrue(future2.isDone());
        assertTrue(future3.isDone());

        // join方法与get方法类似
        String combined = Stream.of(future1, future2, future3).map(CompletableFuture::join)
                .collect(Collectors.joining(" "));

        assertEquals("Hello Beautiful World", combined);
    }

    @Test
    public void whenFutureThrows_thenHandleMethodReceivesException() throws ExecutionException, InterruptedException {
        String name = NULL_STRING;

        // ...

        // handle方法跟whenComplete基本一致，区别在于handle的回调方法有返回值，
        // 且handle方法返回的CompletableFuture的result是回调方法的执行结果或者回调方法执行期间抛出的异常，与原始CompletableFuture的result无关了

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            if (name == null) {
                throw new RuntimeException("Computation error!");
            }
            return "Hello, " + name;
        }).handle((s, t) -> {
            t.printStackTrace();
            
            return s != null ? s : "Hello, Stranger!";
        });

        assertEquals("Hello, Stranger!", completableFuture.get());
    }

    @Test
    public void whenCompletingFutureExceptionally_thenGetMethodThrows()
            throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        // ...

        // 异步执行不正常的结束
        completableFuture.completeExceptionally(new RuntimeException("Calculation failed!"));

        // ...

        assertThrows(ExecutionException.class, () -> completableFuture.get());
    }

    @Test
    public void whenAddingThenApplyAsyncToFuture_thenFunctionExecutesAfterComputationIsFinished()
            throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");

        // 与thenApply区别在于，两个任务在不同的线程中执行
        CompletableFuture<String> future = completableFuture.thenApplyAsync(s -> s + " World");

        assertEquals("Hello World", future.get());
    }

    @Test
    public void whenPassingTransformation_thenFunctionExecutionWithThenApply()
            throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> finalResult = compute().thenApply(s -> s + 1);
        assertTrue(finalResult.get() == 11);
    }

    @Test
    public void whenPassingPreviousStage_thenFunctionExecutionWithThenCompose()
            throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> finalResult = compute().thenCompose(this::computeAnother);
        assertTrue(finalResult.get() == 20);
    }

    public CompletableFuture<Integer> compute() {
        return CompletableFuture.supplyAsync(() -> 10);
    }

    public CompletableFuture<Integer> computeAnother(Integer i) {
        return CompletableFuture.supplyAsync(() -> 10 + i);
    }

}
