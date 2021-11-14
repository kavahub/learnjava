package io.github.kavahub.learnjava;

import org.junit.jupiter.api.Test;

public class JVMBitVersionManualTest {
    @Test
    public void whenUsingSystemClass_thenOutputIsAsExpected() {
        final String version = System.getProperty("sun.arch.data.model");
        System.out.println(version);
    }
    
}
