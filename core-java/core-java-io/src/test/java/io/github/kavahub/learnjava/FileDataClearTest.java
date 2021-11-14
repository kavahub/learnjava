package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileDataClearTest {
    public static final String FILE_DATA_TO_CLEAR = "fileDataToClear.txt";

    @AfterAll
    public static void clearUp() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_DATA_TO_CLEAR));
    }

    @BeforeEach
    public void setup() throws IOException {
        PrintWriter writer = new PrintWriter(FILE_DATA_TO_CLEAR);
        writer.print("This example shows how we can delete the file contents without deleting the file");
        writer.close();
    }
    
    @Test
    public void givenExistingFile_whenDeleteContentUsingPrintWritter_thenEmptyFile() throws IOException {
        PrintWriter writer = new PrintWriter(FILE_DATA_TO_CLEAR);
        writer.print("");
        writer.close();
        assertEquals(0, getStringFromInputStream(new FileInputStream(FILE_DATA_TO_CLEAR)).length());
    }    

    @Test
    public void givenExistingFile_whenDeleteContentUsingPrintWritterWithougObject_thenEmptyFile() throws IOException {
        new PrintWriter(FILE_DATA_TO_CLEAR).close();
        assertEquals(0, getStringFromInputStream(new FileInputStream(FILE_DATA_TO_CLEAR)).length());
    }    
       
    @Test
    public void givenExistingFile_whenDeleteContentUsingFileWriter_thenEmptyFile() throws IOException {
        new FileWriter(FILE_DATA_TO_CLEAR, false).close();
 
        assertEquals(0, getStringFromInputStream(new FileInputStream(FILE_DATA_TO_CLEAR)).length());
    }
    
    @Test
    public void givenExistingFile_whenDeleteContentUsingFileOutputStream_thenEmptyFile() throws IOException {
        new FileOutputStream(FILE_DATA_TO_CLEAR).close();

        assertEquals(0, getStringFromInputStream(new FileInputStream(FILE_DATA_TO_CLEAR)).length());
    }    

    @Test
    public void givenExistingFile_whenDeleteContentUsingFileUtils_thenEmptyFile() throws IOException {
        FileUtils.write(new File(FILE_DATA_TO_CLEAR), "", Charset.defaultCharset());

        assertEquals(0, getStringFromInputStream(new FileInputStream(FILE_DATA_TO_CLEAR)).length());
    }    

    @Test
    public void givenExistingFile_whenDeleteContentUsingNIOFiles_thenEmptyFile() throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_DATA_TO_CLEAR));
        writer.write("");
        writer.flush();
 
        assertEquals(0, getStringFromInputStream(new FileInputStream(FILE_DATA_TO_CLEAR)).length());
    }    
    
    @Test
    public void givenExistingFile_whenDeleteContentUsingNIOFileChannel_thenEmptyFile() throws IOException {
        FileChannel.open(Paths.get(FILE_DATA_TO_CLEAR), StandardOpenOption.WRITE).truncate(0).close();
 
        assertEquals(0, getStringFromInputStream(new FileInputStream(FILE_DATA_TO_CLEAR)).length());
    }   
    
    @Test
    public void givenExistingFile_whenDeleteContentUsingGuava_thenEmptyFile() throws IOException {
        File file = new File(FILE_DATA_TO_CLEAR);
        byte[] empty = new byte[0];
        com.google.common.io.Files.write(empty, file);
        
        assertEquals(0, getStringFromInputStream(new FileInputStream(FILE_DATA_TO_CLEAR)).length());
    }     

    private String getStringFromInputStream(InputStream input) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(input, writer, "UTF-8");

        input.close();
        return writer.toString();
    }
}
