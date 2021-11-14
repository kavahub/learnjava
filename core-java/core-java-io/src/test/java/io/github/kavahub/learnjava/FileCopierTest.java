package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class FileCopierTest {
    private final static File original = new File("src/test/resources/sample.txt");
    private final static String FILE_TO_COPY = "fileToCopy.txt";

    @AfterAll
    public static void clearUp() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_TO_COPY));
    }

    @Test
    public void givenIoAPI_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        File copied = new File(FILE_TO_COPY);
        try (InputStream in = new BufferedInputStream(new FileInputStream(original));
                OutputStream out = new BufferedOutputStream(new FileOutputStream(copied))) {
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
        assertThat(copied).exists();
        assertThat(Files.readAllLines(original.toPath()).equals(Files.readAllLines(copied.toPath())));
    }

    @Test
    public void givenCommonsIoAPI_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        File copied = new File(FILE_TO_COPY);
        FileUtils.copyFile(original, copied);
        assertThat(copied).exists();
        assertThat(Files.readAllLines(original.toPath()).equals(Files.readAllLines(copied.toPath())));
    }

    @Test
    public void givenNIO2_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        Path copied = Paths.get(FILE_TO_COPY);
        Path originalPath = original.toPath();
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        assertThat(copied).exists();
        assertThat(Files.readAllLines(originalPath).equals(Files.readAllLines(copied)));
    }

    @Test
    public void givenGuava_whenCopied_thenCopyExistsWithSameContents() throws IOException {
        File copied = new File(FILE_TO_COPY);
        com.google.common.io.Files.copy(original, copied);
        assertThat(copied).exists();
        assertThat(Files.readAllLines(original.toPath()).equals(Files.readAllLines(copied.toPath())));
    }
}
