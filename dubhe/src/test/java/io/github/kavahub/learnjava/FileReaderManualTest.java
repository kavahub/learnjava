package io.github.kavahub.learnjava;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link FileReader} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FileReaderManualTest {
    @Test
    public void givenFile_whenSystemOut() throws IOException, InterruptedException {
        Path file = Paths.get("src","test","resources", "text.txt");

        FileReader reader = new FileReader(file, (buffer, length) -> {
            System.out.println(new String(buffer.array(), 0, length));
        });

        reader.run();
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void givenFile_whenLogThreadCurrentName_thenMultiThread() throws IOException, InterruptedException {
        Path file = Paths.get("src","test","resources", "text.txt");

        FileReader reader = new FileReader(file, (buffer, length) -> {
            System.out.println(Thread.currentThread().getName());
        });

        reader.run();
        TimeUnit.SECONDS.sleep(3);
    }
}
