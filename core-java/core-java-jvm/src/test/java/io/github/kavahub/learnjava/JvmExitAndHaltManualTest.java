package io.github.kavahub.learnjava;

import org.junit.jupiter.api.Test;

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
