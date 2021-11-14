package io.github.kavahub.learnjava.recordsound;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class RecorderMainManualTest {
    private final static String FILE_NAME = "SoundClip";
    private static File file = new File(FILE_NAME + ".wav");

    @Test
    public void testGetScreenshot() throws Exception {
        RecorderMain.main(null);
        assertTrue(file.exists());
    }

    @AfterAll
    public static void tearDown() throws Exception {
        file.delete();
    }
}
