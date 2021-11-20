package io.github.kavahub.learnjava.util;

import static io.github.kavahub.learnjava.util.HeapDump.dumpHeap;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HeapDumpTest {
    private final static Path FILE_NAME = Paths.get("target", "dump.hprof");

    @Test
    public void testGetScreenshot() throws IOException {
        dumpHeap(FILE_NAME, true);
        assertTrue(Files.exists(FILE_NAME));
    }

    @BeforeAll
    public static void tearDown() throws IOException {
        Files.deleteIfExists(FILE_NAME);
    } 
}
