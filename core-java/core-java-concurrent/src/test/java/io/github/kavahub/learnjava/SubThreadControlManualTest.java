package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class SubThreadControlManualTest {
    @Test
    public void whenStoppedThreadIsStopped() throws InterruptedException {
        SubThreadControl controlSubThread = new SubThreadControl();
        controlSubThread.start();

        TimeUnit.MILLISECONDS.sleep(100);

        // Give things a chance to get set up
        assertTrue(controlSubThread.isRunning());
        assertFalse(controlSubThread.isStopped());

        // Stop it and make sure the flags have been reversed
        controlSubThread.stop();

        assertTrue(controlSubThread.isStopped());
    }

    @Test
    public void whenInterruptedThreadIsStopped() throws InterruptedException {
        SubThreadControl controlSubThread = new SubThreadControl();
        controlSubThread.start();

        TimeUnit.MILLISECONDS.sleep(100);
        // Give things a chance to get set up
        assertTrue(controlSubThread.isRunning());
        assertFalse(controlSubThread.isStopped());

        // Stop it and make sure the flags have been reversed
        controlSubThread.interrupt();

        assertTrue(controlSubThread.isStopped());
    }
}
