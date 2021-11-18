package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

/**
 * 图片文件与Base64字符串互转
 */
public class FileAndBase64StringConversionTest {
    private final static String TEST_IMAGE_COPY = "test_image_copy.jpg";

    @AfterAll
    public static void clear() throws IOException {
        Path path = Paths.get(TEST_IMAGE_COPY);
        Files.deleteIfExists(path);
    }

    @Test
    public void fileToBase64StringConversion() throws IOException {
        //load file from /src/test/resources
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(classLoader
          .getResource("test_image.jpg")
          .getFile());

        byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
        String encodedString = Base64
          .getEncoder()
          .encodeToString(fileContent);

        //create output file
        File outputFile = Paths.get(TEST_IMAGE_COPY).toFile();

        // decode the string and write to file
        byte[] decodedBytes = Base64
          .getDecoder()
          .decode(encodedString);
        FileUtils.writeByteArrayToFile(outputFile, decodedBytes);

        assertTrue(FileUtils.contentEquals(inputFile, outputFile));
    }    
}
