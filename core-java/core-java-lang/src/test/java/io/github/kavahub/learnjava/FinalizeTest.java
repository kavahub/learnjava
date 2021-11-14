package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

public class FinalizeTest {
    @Test
    public void whenGC_thenFinalizerExecuted() throws IOException {
        String firstLine = new Finalizable().readFirstLine();
        assertEquals("learnjava.net", firstLine);
        System.gc();
    }

    @Test
    public void whenTryWResourcesExits_thenResourceClosed() throws IOException {
        try (CloseableResource resource = new CloseableResource()) {
            String firstLine = resource.readFirstLine();
            assertEquals("learnjava.net", firstLine);
        }
    }
    
    public static class CloseableResource implements AutoCloseable {
        private BufferedReader reader;
    
        public CloseableResource() {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("file.txt");
            reader = new BufferedReader(new InputStreamReader(input));
        }
    
        public String readFirstLine() throws IOException {
            String firstLine = reader.readLine();
            return firstLine;
        }
    
        @Override
        public void close() {
            try {
                reader.close();
                System.out.println("Closed BufferedReader in the close method");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    public static class Finalizable {
        private BufferedReader reader;
    
        public Finalizable() {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("file.txt");
            reader = new BufferedReader(new InputStreamReader(input));
        }
    
        public String readFirstLine() throws IOException {
            String firstLine = reader.readLine();
            return firstLine;
        }
    
        @Override
        public void finalize() {
            try {
                reader.close();
                System.out.println("Closed BufferedReader in the finalizer");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
