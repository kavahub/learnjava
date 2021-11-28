package io.github.kavahub.learnjava.util;

import static io.github.kavahub.learnjava.util.ThreadDumpHelper.threadDump;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * {@link ThreadDumpHelper} 类型示例 
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ThreadDumpHelperTest {
    private final static Path FILE_NAME = Paths.get("target", "ThreadDump.txt");

    @Test
    public void testGetScreenshot() throws IOException{
        threadDump(FILE_NAME, true, true);
        assertTrue(Files.exists(FILE_NAME));
    }

    @BeforeAll
    public static void tearDown() throws IOException{
        Files.deleteIfExists(FILE_NAME);
    } 
}
