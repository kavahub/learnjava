package io.github.kavahub.learnjava.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.util.concurrent.Monitor;

import org.junit.jupiter.api.Test;

/**
 * Monitor类在处理互斥操作，同步访问数据块，提供了相比于synchronized关键字更加方便简洁的解决方案
 */
public class MonitorTest {
    @Test
    public void whenGaurdConditionIsTrue_IsSuccessful() {
        Monitor monitor = new Monitor();
        boolean enteredInCriticalSection = false;

        Monitor.Guard gaurdCondition = monitor.newGuard(this::returnTrue);

        if (monitor.enterIf(gaurdCondition)) {
            try {
                System.out.println("Entered in critical section");
                enteredInCriticalSection = true;
            } finally {
                monitor.leave();
            }
        }

        assertTrue(enteredInCriticalSection);

    }

    @Test
    public void whenGaurdConditionIsFalse_IsSuccessful() {
        Monitor monitor = new Monitor();
        boolean enteredInCriticalSection = false;

        Monitor.Guard gaurdCondition = monitor.newGuard(this::returnFalse);

        if (monitor.enterIf(gaurdCondition)) {
            try {
                System.out.println("Entered in critical section");
                enteredInCriticalSection = true;
            } finally {
                monitor.leave();
            }
        }

        assertFalse(enteredInCriticalSection);
    }

    private boolean returnTrue() {
        return true;
    }

    private boolean returnFalse() {
        return false;
    }
}
