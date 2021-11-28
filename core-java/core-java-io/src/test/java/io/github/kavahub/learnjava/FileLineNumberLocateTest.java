package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 文件行定位
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
public class FileLineNumberLocateTest {
    private final static Path FILE_PATH = Paths.get("src", "test", "resources", "linesInput.txt");

    @Test
    public void givenFile_whenUsingBufferedReader_thenExtractedLineIsCorrect() throws IOException {
        try (BufferedReader br = Files.newBufferedReader(FILE_PATH)) {
            for (int i = 0; i < 3; i++) {
                br.readLine();
            }

            String extractedLine = br.readLine();
            assertEquals("Line 4", extractedLine);
        }
    }

    @Test
    public void givenFile_whenUsingScanner_thenExtractedLineIsCorrect() throws IOException {
        try (Scanner scanner = new Scanner(FILE_PATH.toFile())) {
            for (int i = 0; i < 3; i++) {
                scanner.nextLine();
            }

            String extractedLine = scanner.nextLine();
            assertEquals("Line 4", extractedLine);
        }
    }

    @Test
    public void givenSmallFile_whenUsingFilesAPI_thenExtractedLineIsCorrect() throws IOException {
        String extractedLine = Files.readAllLines(FILE_PATH).get(4);

        assertEquals("Line 5", extractedLine);
    }

    @Test
    public void givenLargeFile_whenUsingFilesAPI_thenExtractedLineIsCorrect() throws IOException {
        try (Stream<String> lines = Files.lines(FILE_PATH)) {
            String extractedLine = lines.skip(4).findFirst().get();

            assertEquals("Line 5", extractedLine);
        }
    }

    @Test
    public void givenFile_whenUsingFileUtils_thenExtractedLineIsCorrect() throws IOException {
        List<String> lines = FileUtils.readLines(FILE_PATH.toFile(), "UTF-8");

        String extractedLine = lines.get(1);
        assertEquals("Line 2", extractedLine);
    }

    /**
     * 测试失败不是因为 {@link String#split(String)} 有bug。
     * 
     * <p>
     * 在同一个JVM中，创建多行字符串，是可以正确分割的；而从文件中读取的多行字符串可能
     * 不能正确分割，这是因为这个文件可能是另外一个JVM或程序，在一个环境（如：linux）中创建的。
     * 
     * @throws IOException
     */
    @Test
    @Disabled("linesInput.txt文件是linux下创建的，换行是'\n', 在windows下测试，换行是'\r\n'，所以测试失败")
    public void givenFile_whenUsingIOUtils_thenExtractedLineIsCorrect() throws IOException {
        String fileContent = IOUtils.toString(new FileInputStream(FILE_PATH.toFile()), StandardCharsets.UTF_8);

        String[] extractedLine = fileContent.split(System.lineSeparator());
        assertEquals("Line 1", extractedLine[0]);
    }    

    @Test
    public void givenFile_whenUsingIOUtils_thenExtractedLineIsCorrect1() throws IOException {
        String fileContent = IOUtils.toString(new FileInputStream(FILE_PATH.toFile()), StandardCharsets.UTF_8);

        String[] extractedLine = StringUtils.split(fileContent, System.lineSeparator());
        assertEquals("Line 2", extractedLine[1]);
    }   
}
