package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class HeapDumpHelperManualTest {
    private final static String FILE_NAME = "dump.hprof";
    private static File file = new File(FILE_NAME);

    @Test
    public void testGetScreenshot() throws Exception {
        HeapDumpHelper.dumpHeap(file.getAbsolutePath(), true);
        assertTrue(file.exists());
    }

    @AfterAll
    public static void tearDown() throws Exception {
        file.delete();
    } 
}
