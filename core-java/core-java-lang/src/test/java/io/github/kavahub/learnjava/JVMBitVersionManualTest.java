package io.github.kavahub.learnjava;

import org.junit.jupiter.api.Test;

/**
 * JVM位：64或32
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class JVMBitVersionManualTest {
    @Test
    public void whenUsingSystemClass_thenOutputIsAsExpected() {
        final String version = System.getProperty("sun.arch.data.model");
        System.out.println(version);
    }
    
}
