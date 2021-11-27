package io.github.kavahub.learnjava.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link ListenableFuture} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ListenableFutureTest {
    @Test
    public void whenSubmitToListeningExecutor_thenSuccess() throws ExecutionException, InterruptedException {
        ExecutorService execService = Executors.newSingleThreadExecutor();
        ListeningExecutorService listeningExecService = MoreExecutors.listeningDecorator(execService);

        ListenableFuture<Integer> asyncTask = listeningExecService.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(500); // long running task
            return 5;
        });

        assertEquals(5, asyncTask.get());
    }

    @Test
    public void
    givenJavaExecutor_whenSubmitListeningTask_thenSuccess() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newSingleThreadExecutor();

        FutureTask<String> configFuture = new FutureTask<>(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return String.format("%s.%d", "future.value", new Random().nextInt(Integer.MAX_VALUE));
        });
        executor.execute(configFuture);
        assertTrue(configFuture.get().contains("future.value"));

        ListenableFutureTask<String> configListenableFuture = ListenableFutureTask.create(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return String.format("%s.%d", "listenable.value", new Random().nextInt(Integer.MAX_VALUE));
        });

        executor.execute(configListenableFuture);
        assertTrue(configListenableFuture.get().contains("listenable.value"));
    }

    @Test
    public void givenNonFailingTask_whenCallbackListen_thenSuccess() {
        Executor listeningExecutor = MoreExecutors.directExecutor();

        ListenableFuture<Integer> succeedingTask = Futures.immediateFuture(new Random().nextInt(Integer.MAX_VALUE));
        Futures.addCallback(succeedingTask, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                assertNotNull(result);
                assertTrue(result >= 0);
            }

            @Override
            public void onFailure(Throwable t) {
                fail("Succeeding task cannot failed", t);
            }
        }, listeningExecutor);
    }

    @Test
    public void givenFailingTask_whenCallbackListen_thenThrows() {
        Executor listeningExecutor = MoreExecutors.directExecutor();

        ListenableFuture<Integer> failingTask = Futures.immediateFailedFuture(new ListenableFutureException());
        Futures.addCallback(failingTask, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                fail("Failing task cannot succeed");
            }

            @Override
            public void onFailure(Throwable t) {
                assertTrue(t instanceof ListenableFutureException);
            }
        }, listeningExecutor);
    }

    @Test
    public void givenNonFailingTask_whenDirectListen_thenListenerExecutes() {
        Executor listeningExecutor = MoreExecutors.directExecutor();

        int nextTask = 1;
        Set<Integer> runningTasks = ConcurrentHashMap.newKeySet();
        runningTasks.add(nextTask);

        ListenableFuture<Integer> nonFailingTask = Futures.immediateFuture(new Random().nextInt(Integer.MAX_VALUE));
        nonFailingTask.addListener(() -> runningTasks.remove(nextTask), listeningExecutor);

        assertTrue(runningTasks.isEmpty());
    }

    @Test
    public void givenFailingTask_whenDirectListen_thenListenerExecutes() {
        final Executor listeningExecutor = MoreExecutors.directExecutor();

        int nextTask = 1;
        Set<Integer> runningTasks = ConcurrentHashMap.newKeySet();
        runningTasks.add(nextTask);

        final ListenableFuture<Integer> failingTask = Futures.immediateFailedFuture(new ListenableFutureException());
        failingTask.addListener(() -> runningTasks.remove(nextTask),listeningExecutor);

        assertTrue(runningTasks.isEmpty());
    }

    public class ListeningExecutorServiceWrapper {

        private final ListeningExecutorService lExecService;
    
        public ListeningExecutorServiceWrapper() {
            this.lExecService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        }
    
        public ListeningExecutorServiceWrapper(ListeningExecutorService lExecService) {
            this.lExecService = lExecService;
        }
    
        public ListenableFuture<String> fetchConfig(String configKey) {
            return lExecService.submit(() -> {
                TimeUnit.MILLISECONDS.sleep(500);
                return String.format("%s.%d", configKey, new Random().nextInt(Integer.MAX_VALUE));
            });
        }
    
        public ListenableFuture<Integer> getCartId() {
            return lExecService.submit(() -> {
                TimeUnit.MILLISECONDS.sleep(500);
                return new Random().nextInt(Integer.MAX_VALUE);
            });
        }
    
        public ListenableFuture<String> getCustomerName() {
            String[] names = new String[] { "Mark", "Jane", "June" };
            return lExecService.submit(() -> {
                TimeUnit.MILLISECONDS.sleep(500);
                return names[new Random().nextInt(names.length)];
            });
        }
    
        public ListenableFuture<List<String>> getCartItems() {
            String[] items = new String[] { "Apple", "Orange", "Mango", "Pineapple" };
            return lExecService.submit(() -> {
                TimeUnit.MILLISECONDS.sleep(500);
    
                int noOfItems = new Random().nextInt(items.length);
                if (noOfItems == 0) ++noOfItems;
    
                return Arrays.stream(items, 0, noOfItems).collect(Collectors.toList());
            });
        }
    
        public ListenableFuture<String> generateUsername(String firstName) {
            return lExecService.submit(() -> {
                TimeUnit.MILLISECONDS.sleep(500);
                return firstName.replaceAll("[^a-zA-Z]+","")
                        .concat("@service.com");
            });
        }
    
        public ListenableFuture<String> generatePassword(String username) {
            return lExecService.submit(() -> {
                TimeUnit.MILLISECONDS.sleep(500);
                if (username.contains("@")) {
                    String[] parts = username.split("@");
                    return parts[0] + "123@" + parts[1];
                } else {
                    return username + "123";
                }
            });
        }
    }

    public class ListenableFutureException extends Exception {
    }
}
