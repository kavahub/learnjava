package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class PathEmptinessTest {
    @Test
    public void givenPath_whenInvalid_thenReturnsFalse() throws IOException {
        assertThat(isEmpty(Paths.get("invalid-addr"))).isFalse();
    }

    @Test
    public void givenPath_whenNotDirectory_thenReturnsFalse() throws IOException, URISyntaxException {
        Path aFile = Paths.get(getClass().getResource("/notDir.txt").toURI());
        assertThat(isEmpty(aFile)).isFalse();
    }

    @Test
    public void givenPath_whenNotEmptyDir_thenReturnsFalse() throws IOException {
        Path currentDir = new File("").toPath().toAbsolutePath();
        assertThat(isEmpty(currentDir)).isFalse();

        assertThat(isEmpty2(currentDir)).isFalse();
        assertThat(isEmptyInefficient(currentDir)).isFalse();
    }

    @Test
    public void givenPath_whenIsEmpty_thenReturnsTrue() throws Exception {
        Path path = Files.createTempDirectory("emptyDir");
        assertThat(isEmpty(path)).isTrue();

        assertThat(isEmpty2(path)).isTrue();
        assertThat(isEmptyInefficient(path)).isTrue();
    }

    private static boolean isEmpty(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
                return !directory.iterator().hasNext();
            }
        }

        return false;
    }

    private static boolean isEmpty2(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                return !entries.findFirst().isPresent();
            }
        }

        return false;
    }

    private static boolean isEmptyInefficient(Path path) {
        return path.toFile().listFiles().length == 0;
    }
}
