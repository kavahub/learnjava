package io.github.kavahub.learnjava.output;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class FileOutputStreamHelperTest {
    private final static String OUTPUT_FILE = "output_file.txt";
    
    @AfterEach
    public void eachTest() throws IOException {
        Files.deleteIfExists(Paths.get(OUTPUT_FILE));
    }

    @Test
    public void givenOutputStream_whenWriteSingleByteCalled_thenOutputCreated() throws IOException {
        
        final File file = new File(OUTPUT_FILE);    
        FileOutputStreamHelper.fileOutputStreamByteSingle(OUTPUT_FILE, "Hello World!");
        assertTrue(file.exists());
        file.delete();
    }
    
    @Test
    public void givenOutputStream_whenWriteByteSequenceCalled_thenOutputCreated() throws IOException {
        
        final File file = new File(OUTPUT_FILE);      
        FileOutputStreamHelper.fileOutputStreamByteSequence(OUTPUT_FILE, "Hello World!");
        assertTrue(file.exists());
    }
    
    @Test
    public void givenOutputStream_whenWriteByteSubSequenceCalled_thenOutputCreated() throws IOException {
        
        final File file = new File(OUTPUT_FILE);      
        FileOutputStreamHelper.fileOutputStreamByteSubSequence(OUTPUT_FILE, "Hello World!");
        assertTrue(file.exists());
    }

    @Test
    public void givenBufferedOutputStream_whenCalled_thenOutputCreated() throws IOException {
        final File file = new File(OUTPUT_FILE);    
        FileOutputStreamHelper.bufferedOutputStream(OUTPUT_FILE, "Hello", "World!");
        assertTrue(file.exists());
    }
    
    @Test
    public void givenOutputStreamWriter_whenCalled_thenOutputCreated() throws IOException {
        
        final File file = new File(OUTPUT_FILE);     
        FileOutputStreamHelper.outputStreamWriter(OUTPUT_FILE, "Hello World!");
        assertTrue(file.exists());
    }   
}
