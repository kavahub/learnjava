package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;

import jakarta.activation.MimetypesFileTypeMap;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

/**
 * 确定文件类型
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
public class MimeTypeTest {
    /**
     * Expected Ouput.
     */
    public static final String PNG_EXT = "image/png";

    /**
     * The location of the file.
     */
    public static final Path FILE_LOC = Paths.get("src","test","resources","product");

    /**
     * Test method, demonstrating usage in Java 7.
     * 
     * <p>
     * 使用扩展名判断
     * 
     * @throws IOException
     */
    @Test
    public void whenUsingJava7_thenSuccess() throws IOException {
        final String mimeType = Files.probeContentType(FILE_LOC);
        assertNotEquals(PNG_EXT, mimeType);
    }

    /**
     * Test method demonstrating the usage of URLConnection to resolve MIME type.
     * 
     * @throws MalformedURLException
     * @throws IOException
     */
    @Test
    public void whenUsingGetContentType_thenSuccess() throws MalformedURLException, IOException {
        final File file = FILE_LOC.toFile();
        final URLConnection connection = file.toURI().toURL().openConnection();
        final String mimeType = connection.getContentType();
        assertEquals(PNG_EXT, mimeType);
    }

    /**
     * Test method demonstrating the usage of URLConnection to resolve MIME type.
     * 
     * <p>
     * 使用扩展名判断
     * 
     */
    @Test
    public void whenUsingGuessContentTypeFromName_thenSuccess() {
        final File file = FILE_LOC.toFile();
        final String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        assertNotEquals(PNG_EXT, mimeType);
    }

    /**
     * Test method demonstrating the usage of FileNameMap from URLConnection to
     * resolve MIME type of a file.
     * 
     * <p>
     * 使用扩展名判断
     */
    @Test
    public void whenUsingGetFileNameMap_thenSuccess() {
        final File file = FILE_LOC.toFile();
        final FileNameMap fileNameMap = URLConnection.getFileNameMap();
        final String mimeType = fileNameMap.getContentTypeFor(file.getName());
        assertNotEquals(PNG_EXT, mimeType);
    }

    /**
     * Test method demonstrating the usage of MimeTypesFileTypeMap for resolution of
     * MIME type.
     * 
     * <p>
     * 使用扩展名判断
     * 
     */
    @Test
    public void whenUsingMimeTypesFileTypeMap_thenSuccess() {
        final File file = FILE_LOC.toFile();
        final MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        final String mimeType = fileTypeMap.getContentType(file.getName());
        assertNotEquals(PNG_EXT, mimeType);
    }

    /**
     * Test method demonstrating usage of jMimeMagic.
     * 
     * @throws MagicParseException
     * @throws MagicMatchNotFoundException
     * @throws MagicException
     */
    @Test
    public void whenUsingJmimeMagic_thenSuccess()
            throws MagicParseException, MagicMatchNotFoundException, MagicException {
        final File file = FILE_LOC.toFile();
        final MagicMatch match = Magic.getMagicMatch(file, false);
        assertEquals(PNG_EXT, match.getMimeType());
    }

    /**
     * Test method demonstrating usage of Apache Tika.
     * 
     * @throws IOException
     */
    @Test
    public void whenUsingTika_thenSuccess() throws IOException {
        final File file = FILE_LOC.toFile();
        final Tika tika = new Tika();
        final String mimeType = tika.detect(file);
        assertEquals(mimeType, PNG_EXT);
    }
}
