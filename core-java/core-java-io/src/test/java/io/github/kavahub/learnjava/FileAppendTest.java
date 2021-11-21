package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 文件追加示例
 */
public class FileAppendTest {
    public static final Path FIEL_TO_APPEND = Paths.get("target","fileToAppend.txt");
    public static final String EXPEC_STRING = "UK\r\n" + "US\r\n" + "Germany\r\n" + "中文\r\n" + "Spain\r\n";

    @BeforeEach
    public void setup() throws Exception {
        Files.deleteIfExists(FIEL_TO_APPEND);

        PrintWriter writer = new PrintWriter(FIEL_TO_APPEND.toFile());
        writer.print("UK\r\n" + "US\r\n" + "Germany\r\n" + "中文\r\n");
        writer.close();
    }

    @Test
    public void whenAppendToFileUsingGuava_thenCorrect() throws IOException {
        CharSink chs = com.google.common.io.Files.asCharSink(FIEL_TO_APPEND.toFile(), Charsets.UTF_8, FileWriteMode.APPEND);
        chs.write("Spain\r\n");
        
        assertThat(getStringFromInputStream(new FileInputStream(FIEL_TO_APPEND.toFile()))).isEqualTo(EXPEC_STRING);
    }

    @Test
    public void whenAppendToFileUsingFiles_thenCorrect() throws IOException {
        Files.write(FIEL_TO_APPEND, "Spain\r\n".getBytes(), StandardOpenOption.APPEND);

        assertThat(getStringFromInputStream(new FileInputStream(FIEL_TO_APPEND.toFile()))).isEqualTo(EXPEC_STRING);
    }

    @Test
    public void whenAppendToFileUsingFileUtils_thenCorrect() throws IOException {
        FileUtils.writeStringToFile(FIEL_TO_APPEND.toFile(), "Spain\r\n", StandardCharsets.UTF_8, true);

        assertThat(getStringFromInputStream(new FileInputStream(FIEL_TO_APPEND.toFile()))).isEqualTo(EXPEC_STRING);
    }

    @Test
    public void whenAppendToFileUsingFileOutputStream_thenCorrect() throws Exception {
        FileOutputStream fos = new FileOutputStream(FIEL_TO_APPEND.toFile(), true);
        fos.write("Spain\r\n".getBytes());
        fos.close();

        assertThat(getStringFromInputStream(new FileInputStream(FIEL_TO_APPEND.toFile()))).isEqualTo(EXPEC_STRING);
    }

    @Test
    public void whenAppendToFileUsingFileWriter_thenCorrect() throws IOException {
        FileWriter fw = new FileWriter(FIEL_TO_APPEND.toFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Spain");
        bw.newLine();
        bw.close();

        assertThat(getStringFromInputStream(new FileInputStream(FIEL_TO_APPEND.toFile()))).isEqualTo(EXPEC_STRING);
    } 


    private String getStringFromInputStream(InputStream input) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(input, writer, "UTF-8");

        input.close();
        return writer.toString();
    }
}
