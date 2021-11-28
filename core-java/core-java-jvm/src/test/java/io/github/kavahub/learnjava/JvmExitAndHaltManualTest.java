package io.github.kavahub.learnjava;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link JvmExitAndHalt} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class JvmExitAndHaltManualTest {
    JvmExitAndHalt jvm = new JvmExitAndHalt();

    @Test
    public void givenProcessComplete_whenHaltCalled_thenDoNotTriggerShutdownHook() {
        jvm.processAndHalt();
    }

    @Test
    public void givenProcessComplete_whenExitCalled_thenTriggerShutdownHook() {
        jvm.processAndExit();
    }

}
