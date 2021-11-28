package io.github.kavahub.learnjava.threadlocal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link ThreadLocal} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ThreadLocalTest {
    @Test
    public void givenThreadThatStoresContextInAMap_whenStartThread_thenShouldSetContextForBothUsers() throws ExecutionException, InterruptedException {
        //when
        SharedMapWithContext firstUser = new SharedMapWithContext(1);
        SharedMapWithContext secondUser = new SharedMapWithContext(2);
        new Thread(firstUser).start();
        new Thread(secondUser).start();

        Thread.sleep(3000);
        //then
        assertEquals(SharedMapWithContext.context.size(), 2);
    }

    @Test
    public void givenThreadThatStoresContextInThreadLocal_whenStartThread_thenShouldStoreContextInThreadLocal() throws ExecutionException, InterruptedException {
        //when
        ThreadLocalWithContext firstUser = new ThreadLocalWithContext(1);
        ThreadLocalWithContext secondUser = new ThreadLocalWithContext(2);
        new Thread(firstUser).start();
        new Thread(secondUser).start();

        Thread.sleep(3000);
    }
}
