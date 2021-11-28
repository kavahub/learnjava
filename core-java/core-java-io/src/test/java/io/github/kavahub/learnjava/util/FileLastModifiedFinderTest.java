package io.github.kavahub.learnjava.util;

import static io.github.kavahub.learnjava.util.FileLastModifiedFinder.findUsingCommonsIO;
import static io.github.kavahub.learnjava.util.FileLastModifiedFinder.findUsingIOApi;
import static io.github.kavahub.learnjava.util.FileLastModifiedFinder.findUsingNIOApi;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link FileLastModifiedFinder} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FileLastModifiedFinderTest {
    private final static String SOURCEDIRECTORY = Paths.get("target", "lastmodfiles").toString();

    @BeforeAll
    public static void setUpFiles() throws IOException, InterruptedException {
        File srcDir = new File(SOURCEDIRECTORY);
        FileUtils.deleteDirectory(srcDir);

        if (!srcDir.exists()) {
            srcDir.mkdir();
        }

        FileUtils.cleanDirectory(srcDir);

        File file01 = new File(SOURCEDIRECTORY + "/file01.txt");
        file01.createNewFile();

        Thread.sleep(2000);

        File file02 = new File(SOURCEDIRECTORY + "/file02.txt");
        file02.createNewFile();

        Thread.sleep(2000);

        File file03 = new File(SOURCEDIRECTORY + "/file03.txt");
        file03.createNewFile();

        Thread.sleep(2000);

        Files.write(Paths.get(SOURCEDIRECTORY + "/file02.txt"), "Hello File02".getBytes());
    }

    @Test
    public void givenDirectory_whenUsingIoApi_thenFindLastModfile() throws IOException {
        File lastModFile = findUsingIOApi(SOURCEDIRECTORY);

        assertThat(lastModFile).isNotNull();
        assertThat(lastModFile.getName()).isEqualTo("file02.txt");
    }

    @Test
    public void givenDirectory_whenUsingNioApi_thenFindLastModfile() throws IOException {
        Path lastModPath = findUsingNIOApi(SOURCEDIRECTORY);

        assertThat(lastModPath).isNotNull();
        assertThat(lastModPath.toFile().getName()).isEqualTo("file02.txt");
    }

    @Test
    public void givenDirectory_whenUsingApacheCommons_thenFindLastModfile() throws IOException {
        File lastModFile = findUsingCommonsIO(SOURCEDIRECTORY);

        assertThat(lastModFile).isNotNull();
        assertThat(lastModFile.getName()).isEqualTo("file02.txt");
    }


}
