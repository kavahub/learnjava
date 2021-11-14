package io.github.kavahub.learnjava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReadLargeFileTest {
    private final static String LARGE_FILE = "src/test/resources/largefile.txt".replace("/", File.separator);

    // tests - iterate lines in a file

    @Test
    public final void givenUsingGuava_whenIteratingAFile_thenCorrect() throws IOException {
        logMemory();
        Files.readLines(new File(LARGE_FILE), Charsets.UTF_8);
        logMemory();
    }

    @Test
    public final void givenUsingCommonsIo_whenIteratingAFileInMemory_thenCorrect() throws IOException {
        logMemory();
        FileUtils.readLines(new File(LARGE_FILE), StandardCharsets.UTF_8);
        logMemory();
    }

    @Test
    public final void whenStreamingThroughAFile_thenCorrect() throws IOException {
        logMemory();

        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream(LARGE_FILE);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                sc.nextLine();
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }

        logMemory();
    }

    @Test
    public final void givenUsingApacheIo_whenStreamingThroughAFile_thenCorrect() throws IOException {
        logMemory();

        final LineIterator it = FileUtils.lineIterator(new File(LARGE_FILE), "UTF-8");
        try {
            while (it.hasNext()) {
                it.nextLine();
            }
        } finally {
            it.close();
        }

        logMemory();
    }

    // utils

    private final void logMemory() {
        log.info("Max Memory: {} Mb", Runtime.getRuntime().maxMemory() / 1048576);
        log.info("Total Memory: {} Mb", Runtime.getRuntime().totalMemory() / 1048576);
        log.info("Free Memory: {} Mb", Runtime.getRuntime().freeMemory() / 1048576);
    }

}
