package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class ScreenshotManualTest {
    private final static String FILE_NAME = "Screenshot.jpg";
    private Screenshot screenshot = new Screenshot(FILE_NAME);
    private static File file = new File(FILE_NAME);

    @Test
    public void testGetScreenshot() throws Exception {
        screenshot.getScreenshot(2000);
        assertTrue(file.exists());
    }

    @AfterAll
    public static void tearDown() throws Exception {
        file.delete();
    }
}
