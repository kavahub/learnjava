package io.github.kavahub.learnjava.util;

import static io.github.kavahub.learnjava.util.FileOutputStreamWriter.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link FileOutputStreamWriter} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FileOutputStreamWriterTest {
    private final static Path OUTPUT_FILE = Paths.get("target", "output_file.txt");
    
    @BeforeEach
    public void eachTest() throws IOException {
        Files.deleteIfExists(OUTPUT_FILE);
    }

    @Test
    public void givenOutputStream_whenWriteSingleByteCalled_thenOutputCreated() throws IOException {
        fileOutputStreamByteSingle(OUTPUT_FILE.toString(), "Hello World!");
        assertTrue(Files.exists(OUTPUT_FILE));
    }
    
    @Test
    public void givenOutputStream_whenWriteByteSequenceCalled_thenOutputCreated() throws IOException {   
        fileOutputStreamByteSequence(OUTPUT_FILE.toString(), "Hello World!");
        assertTrue(Files.exists(OUTPUT_FILE));
    }
    
    @Test
    public void givenOutputStream_whenWriteByteSubSequenceCalled_thenOutputCreated() throws IOException {  
        fileOutputStreamByteSubSequence(OUTPUT_FILE.toString(), "Hello World!");
        assertTrue(Files.exists(OUTPUT_FILE));
    }

    @Test
    public void givenBufferedOutputStream_whenCalled_thenOutputCreated() throws IOException {
        bufferedOutputStream(OUTPUT_FILE.toString(), "Hello", "World!");
        assertTrue(Files.exists(OUTPUT_FILE));
    }
    
    @Test
    public void givenOutputStreamWriter_whenCalled_thenOutputCreated() throws IOException {    
        outputStreamWriter(OUTPUT_FILE.toString(), "Hello World!");
        assertTrue(Files.exists(OUTPUT_FILE));
    }   
}
