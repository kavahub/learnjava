package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 
 * Java 文件路径示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class JavaFilePathTest {
    private static String userDir;
    private static String sep = File.separator;

    @BeforeAll
    public static void createFilesAndFolders() throws IOException {
        userDir = System.getProperty("user.dir");

        new File(userDir + "/learnjava/foo".replace("/", sep)).mkdirs();
        new File(userDir + "/learnjava/bar/baz".replace("/", sep)).mkdirs();
        new File(userDir + "/learnjava/foo/foo-one.txt".replace("/", sep)).createNewFile();
        new File(userDir + "/learnjava/foo/foo-two.txt".replace("/", sep)).createNewFile();
        new File(userDir + "/learnjava/bar/bar-one.txt".replace("/", sep)).createNewFile();
        new File(userDir + "/learnjava/bar/bar-two.txt".replace("/", sep)).createNewFile();
        new File(userDir + "/learnjava/bar/baz/baz-one.txt".replace("/", sep)).createNewFile();
        new File(userDir + "/learnjava/bar/baz/baz-two.txt".replace("/", sep)).createNewFile();

    }

    @Test
    public void whenPathResolved_thenSuccess() {
        final String expectedPath = "learnjava/foo/foo-one.txt".replace("/", sep);

        File file = new File(expectedPath);
        String actualPath = file.getPath();
        assertEquals(expectedPath, actualPath);
    }

    @Test
    public void whenAbsolutePathResolved_thenSuccess() {
        final String expectedPath = "learnjava/foo/foo-one.txt".replace("/", sep);

        File file = new File(expectedPath);
        String actualPath = file.getAbsolutePath();
        assertEquals(userDir + sep + expectedPath, actualPath);
    }

    @Test
    public void whenAbsolutePathWithShorthandResolved_thenSuccess() {
        final String expectedPath = "learnjava/bar/baz/../bar-one.txt".replace("/", sep);

        File file = new File(expectedPath);
        String actualPath = file.getAbsolutePath();
        assertEquals(userDir + sep + expectedPath, actualPath);
    }

    @Test
    public void whenCanonicalPathWithShorthandResolved_thenSuccess() throws IOException {
        final String path = "learnjava/bar/baz/../bar-one.txt".replace("/", sep);

        File file = new File(path);
        String actualPath = file.getCanonicalPath();
        assertEquals(userDir + "/learnjava/bar/bar-one.txt".replace("/", sep), actualPath);
    }

    @Test
    public void whenCanonicalPathWithDotShorthandResolved_thenSuccess() throws IOException {
        final String path = "learnjava/bar/baz/./bar-one.txt".replace("/", sep);

        File file = new File(path);
        String actualPath = file.getCanonicalPath();
        assertEquals(userDir + "/learnjava/bar/baz/bar-one.txt".replace("/", sep), actualPath);
    }

    @Test
    public void givenWindowsOs_whenCanonicalPathWithWildcard_thenIOException() throws IOException {
        Assumptions.assumeTrue(isWindows());

        assertThrows(IOException.class, () -> new File("*").getCanonicalPath());
    }

    @AfterAll
    public static void deleteFilesAndFolders() {
        File learnjavaDir = new File(userDir + "/learnjava");
        deleteRecursively(learnjavaDir);
    }

    private static void deleteRecursively(File dir) {
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                deleteRecursively(f);
            }
            f.delete();
        }
        dir.delete();
    }

    private static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName.contains("Windows");
    }    
}
