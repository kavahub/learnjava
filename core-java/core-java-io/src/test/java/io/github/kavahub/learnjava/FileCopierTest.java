package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 文件复制示例
 */
public class FileCopierTest {
    private final static Path original = Paths.get("src", "test", "resources", "sample.txt");
    private final static Path FILE_TO_COPY = Paths.get("target","fileToCopy.txt");

    @BeforeEach
    public void clearUp() throws IOException {
        Files.deleteIfExists(FILE_TO_COPY);
    }

    @Test
    public void givenIoAPI_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        try (InputStream in = new BufferedInputStream(new FileInputStream(original.toFile()));
                OutputStream out = new BufferedOutputStream(new FileOutputStream(FILE_TO_COPY.toFile()))) {
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
        assertThat(FILE_TO_COPY).exists();
        assertThat(Files.readAllLines(original).equals(Files.readAllLines(FILE_TO_COPY)));
    }

    @Test
    public void givenCommonsIoAPI_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        FileUtils.copyFile(original.toFile(), FILE_TO_COPY.toFile());
        assertThat(FILE_TO_COPY).exists();
        assertThat(Files.readAllLines(original).equals(Files.readAllLines(FILE_TO_COPY)));
    }

    @Test
    public void givenNIO2_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        Files.copy(original, FILE_TO_COPY, StandardCopyOption.REPLACE_EXISTING);
        assertThat(FILE_TO_COPY).exists();
        assertThat(Files.readAllLines(original).equals(Files.readAllLines(FILE_TO_COPY)));
    }

    @Test
    public void givenGuava_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        com.google.common.io.Files.copy(original.toFile(), FILE_TO_COPY.toFile());
        assertThat(FILE_TO_COPY).exists();
        assertThat(Files.readAllLines(original).equals(Files.readAllLines(FILE_TO_COPY)));
    }
}
