package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * {@link Thread#interrupt()} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class ThreadInterruptTest {

    @Test
    public void whenPropagateException_thenThrowsInterruptedException() {
        assertThrows(InterruptedException.class, () -> propagateException());
    }

    @Test
    public void givenSleep_whenRestoreTheState_thenReturnsTrue() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(9000);
                } catch (InterruptedException e) {
                    log.info("{} interrupted", Thread.currentThread().getName());
                    // Thread.currentThread().interrupt();
                }
            }

        };
        assertTrue(restoreTheState(thread));
    }

    @Test
    public void givenWhile_whenRestoreTheState_thenReturnsTrue() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                final long worktime = 9 * 1000;
                final long now = System.currentTimeMillis();
                try {
                    while (System.currentTimeMillis() <= now + worktime) {

                    }
                } catch (Exception e) {
                    log.info("{} interrupted", Thread.currentThread().getName());
                    // Thread.currentThread().interrupt();
                }
            }

        };
        assertTrue(restoreTheState(thread));
    }

    @Test
    public void whenThrowCustomException_thenContainsExpectedMessage() {
        Exception exception = assertThrows(CustomInterruptedException.class, () -> throwCustomException());
        String expectedMessage = "This thread was interrupted";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenHandleWithCustomException_thenReturnsTrue() throws CustomInterruptedException {
        assertTrue(handleWithCustomException());
    }

    public static void propagateException() throws InterruptedException {
        Thread.sleep(1000);
        Thread.currentThread().interrupt();
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    public Boolean restoreTheState(Thread thread) {
        thread.start();
        thread.interrupt();
        return thread.isInterrupted();
    }

    public void throwCustomException() throws Exception {
        Thread.sleep(1000);
        Thread.currentThread().interrupt();
        if (Thread.interrupted()) {
            throw new CustomInterruptedException("This thread was interrupted");
        }
    }

    public Boolean handleWithCustomException() throws CustomInterruptedException {
        try {
            Thread.sleep(1000);
            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            log.info("{} interrupted", Thread.currentThread().getName());
            // Thread.currentThread().interrupt();
            throw new CustomInterruptedException("This thread was interrupted...");
        }
        return Thread.currentThread().isInterrupted();
    }

    public static class CustomInterruptedException extends Exception {

        private static final long serialVersionUID = 1L;

        CustomInterruptedException(String message) {
            super(message);
        }
    }
}
