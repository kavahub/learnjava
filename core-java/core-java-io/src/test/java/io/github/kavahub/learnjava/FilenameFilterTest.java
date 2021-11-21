package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link FilenameFilter} 示例
 */
@Slf4j
public class FilenameFilterTest {
    private static File directory = Paths.get("src", "test", "resources", "fileNameFilterManualTestFolder").toFile();

    /**
     * list方法会扫描子目录，单不扫描子目录下的文件
     */
    @Test
    public void whenFilteringLogFileInfo_thenEqualExpectedFiles() {
        FilenameFilter filter = (dir, name) -> {
            log.info("File: {}", name.toString());
            return true;
        };

        directory.list(filter);
    }

    @Test
    public void whenFilteringFilesEndingWithJson_thenEqualExpectedFiles() {
        FilenameFilter filter = (dir, name) -> name.endsWith(".json");

        String[] expectedFiles = { "people.json", "students.json" };
        String[] actualFiles = directory.list(filter);
        assertArrayEquals(expectedFiles, actualFiles);
    }

    @Test
    public void whenFilteringFilesEndingWithXml_thenEqualExpectedFiles() {
        Predicate<String> predicate = (name) -> name.endsWith(".xml");

        String[] expectedFiles = { "teachers.xml", "workers.xml" };
        List<String> files = Arrays.stream(directory.list()).filter(predicate).collect(Collectors.toList());
        String[] actualFiles = files.toArray(new String[files.size()]);

        assertArrayEquals(expectedFiles, actualFiles);
    }

}
