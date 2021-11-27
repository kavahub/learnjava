package io.github.kavahub.learnjava.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncCallable;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link ListeningExecutorService} 继承 <code>ExecutorService</code>
 *
 * <p>
 * {@link MoreExecutors} 是对jdk自带的Executors工具类的扩展， 创建 <code>ListeningExecutorService</code> 实例
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ListenableFutureComplexTest {
    @Test
    public void givenAllSucceedingTasks_whenAllAsList_thenAllSuccess() {
        final ListeningExecutorService listeningExecService = MoreExecutors.newDirectExecutorService();
        final ListenableFutureService service = new ListenableFutureService(listeningExecService);

        ListenableFuture<String> task1 = service.fetchConfig("config.0");
        ListenableFuture<String> task2 = service.fetchConfig("config.1");
        ListenableFuture<String> task3 = service.fetchConfig("config.2");

        ListenableFuture<List<String>> configsTask = Futures.allAsList(task1, task2, task3);
        Futures.addCallback(configsTask, new FutureCallback<List<String>>() {
            @Override
            public void onSuccess(@Nullable List<String> configResults) {
                assertNotNull(configResults);
                assertEquals(3, configResults.size());
                for (int i = 0; i < 3; i++) {
                    assertTrue(configResults.get(i)
                        .contains("config." + i));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                fail("Unexpected failure detected", t);
            }
        }, listeningExecService);
    }

    @Test
    public void givenOneFailingTask_whenAllAsList_thenFailure() {
        final ListeningExecutorService listeningExecService = MoreExecutors.newDirectExecutorService();
        final ListenableFutureService service = new ListenableFutureService(listeningExecService);

        ListenableFuture<String> task1 = service.fetchConfig("config.0");
        ListenableFuture<String> task2 = service.failingTask();
        ListenableFuture<String> task3 = service.fetchConfig("config.2");

        ListenableFuture<List<String>> configsTask = Futures.allAsList(task1, task2, task3);
        Futures.addCallback(configsTask, new FutureCallback<List<String>>() {
            @Override
            public void onSuccess(@Nullable List<String> configResults) {
                fail("Expected a failed future");
            }

            @Override
            public void onFailure(Throwable t) {
                assertTrue(t instanceof ListenableFutureException);
            }
        }, listeningExecService);
    }

    @Test
    public void givenOneFailingTask_whenSuccessfulAsList_thenSomeSuccess() {
        final ListeningExecutorService listeningExecService = MoreExecutors.newDirectExecutorService();
        final ListenableFutureService service = new ListenableFutureService(listeningExecService);

        ListenableFuture<String> task1 = service.fetchConfig("config.0");
        ListenableFuture<String> task2 = service.failingTask();
        ListenableFuture<String> task3 = service.fetchConfig("config.2");

        ListenableFuture<List<String>> configsTask = Futures.successfulAsList(task1, task2, task3);

        Futures.addCallback(configsTask, new FutureCallback<List<String>>() {
            @Override
            public void onSuccess(@Nullable List<String> configResults) {
                assertNotNull(configResults);
                assertTrue(configResults.get(0).contains("config.0"));
                assertNull(configResults.get(1));
                assertTrue(configResults.get(2).contains("config.2"));
            }

            @Override
            public void onFailure(Throwable t) {
                fail("Unexpected failure detected", t);
            }
        }, listeningExecService);
    }

    @Test
    public void givenAllSucceedingTasks_whenAllSucceed_thenSuccess() {
        ListeningExecutorService listeningExecService = MoreExecutors.newDirectExecutorService();
        ListenableFutureService service = new ListenableFutureService(listeningExecService);

        ListenableFuture<Integer> cartIdTask = service.getCartId();
        ListenableFuture<String> customerNameTask = service.getCustomerName();
        ListenableFuture<List<String>> cartItemsTask = service.getCartItems();

        ListenableFuture<CartInfo> cartInfoTask = Futures
            // 所有的任务运行完成
            .whenAllSucceed(cartIdTask, customerNameTask, cartItemsTask)
            // 然后执行
            .call(() -> {
                // 获取任务返回值
                int cartId = Futures.getDone(cartIdTask);
                String customerName = Futures.getDone(customerNameTask);
                List<String> cartItems = Futures.getDone(cartItemsTask);

                return new CartInfo(cartId, customerName, cartItems);
            }, listeningExecService);

        // 任务完成后回调
        Futures.addCallback(cartInfoTask, new FutureCallback<CartInfo>() {
            @Override
            public void onSuccess(@Nullable CartInfo result) {
                assertNotNull(result);
                assertTrue(result.cartId >= 0);
                assertFalse(result.customerName.isEmpty());
                assertFalse(result.cartItems.isEmpty());
            }

            @Override
            public void onFailure(Throwable t) {
                fail("Unexpected failure detected", t);
            }
        }, listeningExecService);
    }

    @Test
    public void givenAllSucceedingTasks_whenAllComplete_thenSomeSuccess() {
        ListeningExecutorService listeningExecService = MoreExecutors.newDirectExecutorService();
        ListenableFutureService service = new ListenableFutureService(listeningExecService);

        ListenableFuture<Integer> cartIdTask = service.getCartId();
        ListenableFuture<String> customerNameTask = service.failingTask();
        ListenableFuture<List<String>> cartItemsTask = service.getCartItems();

        ListenableFuture<CartInfo> cartInfoTask = Futures.whenAllComplete(cartIdTask, customerNameTask, cartItemsTask)
                .call(() -> {
                    Integer cartId = getOrNull(cartIdTask);
                    String customerName = getOrNull(customerNameTask);
                    List<String> cartItems = getOrNull(cartItemsTask);
                    return new CartInfo(cartId, customerName, cartItems);
                }, listeningExecService);

        Futures.addCallback(cartInfoTask, new FutureCallback<CartInfo>() {
            @Override
            public void onSuccess(@Nullable CartInfo result) {
                assertNotNull(result);
                assertTrue(result.cartId >= 0);
                assertNull(result.customerName);
                assertFalse(result.cartItems.isEmpty());
            }

            @Override
            public void onFailure(Throwable t) {
                fail("Unexpected failure detected", t);
            }
        }, listeningExecService);
    }

    @Test
    public void whenTransform_thenTransformSuccess() {
        ListeningExecutorService listenExecService = MoreExecutors.newDirectExecutorService();
        ListenableFutureService service = new ListenableFutureService(listenExecService);

        ListenableFuture<List<String>> cartItemsTask = service.getCartItems();

        Function<List<String>, Integer> itemCountFunc = cartItems -> {
            assertNotNull(cartItems);
            return cartItems.size();
        };

        ListenableFuture<Integer> itemCountTask = Futures.transform(cartItemsTask, itemCountFunc, listenExecService);

        Futures.addCallback(itemCountTask, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(@Nullable Integer cartItemCount) {
                assertNotNull(cartItemCount);
                assertTrue(cartItemCount > 0);
            }

            @Override
            public void onFailure(Throwable t) {
                fail("Unexpected failure detected", t);
            }
        }, listenExecService);
    }

    @Test
    public void whenSubmitAsync_thenSuccess() {
        ListeningExecutorService executor = MoreExecutors.newDirectExecutorService();
        ListenableFutureService service = new ListenableFutureService(executor);

        AsyncCallable<String> asyncConfigTask = () -> {
            ListenableFuture<String> configTask = service.fetchConfig("config.a");
            TimeUnit.MILLISECONDS.sleep(500); //some long running task
            return configTask;
        };

        ListenableFuture<String> configTask = Futures.submitAsync(asyncConfigTask, executor);

        Futures.addCallback(configTask, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                assertNotNull(result);
                assertTrue(result.contains("config.a"));
            }

            @Override
            public void onFailure(Throwable t) {
                fail("Unexpected failure detected", t);
            }
        }, executor);
    }

    @Test
    public void whenAsyncTransform_thenSuccess() {
        ListeningExecutorService executor = MoreExecutors.newDirectExecutorService();
        ListenableFutureService service = new ListenableFutureService(executor);
        
        ListenableFuture<String> usernameTask = service.generateUsername("john");
        AsyncFunction<String, String> passwordFunc = username -> {
            ListenableFuture<String> generatePasswordTask = service.generatePassword(username);
            TimeUnit.MILLISECONDS.sleep(500); // some long running task
            return generatePasswordTask;
        };

        ListenableFuture<String> passwordTask = Futures.transformAsync(usernameTask, passwordFunc, executor);

        Futures.addCallback(passwordTask, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String password) {
                assertNotNull(password);
                assertTrue(password.contains("john"));
                assertTrue(password.contains("@"));
            }

            @Override
            public void onFailure(Throwable t) {
                fail("Unexpected failure detected", t);
            }
        }, executor);
    }

    private static <T> T getOrNull(ListenableFuture<T> future) {
        try {
            return Futures.getDone(future);
        } catch (ExecutionException e) {
            return null;
        }
    }

    static class CartInfo {
        Integer cartId;
        String customerName;
        List<String> cartItems;

        public CartInfo(Integer cartId, String customerName, List<String> cartItems) {
            this.cartId = cartId;
            this.customerName = customerName;
            this.cartItems = cartItems;
        }
    }

    public class ListenableFutureService {

        private final ListeningExecutorService lExecService;
    
        public <T> ListenableFuture<T> failingTask() {
            return Futures.immediateFailedFuture(new ListenableFutureException());
        }
        
        public ListenableFutureService(ListeningExecutorService lExecService) {
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
