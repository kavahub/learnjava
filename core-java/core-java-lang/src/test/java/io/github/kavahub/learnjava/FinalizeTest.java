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

    /**
     * AutoCloseable的注释可知它的出现是为了更好的管理资源，准确说是资源的释放，当一个资源类实现了该接口close方法，
     * 在使用try-catch-resources语法创建的资源抛出异常后，JVM会自动调用close 方法进行资源释放，
     * 当没有抛出异常正常退出try-block时候也会调用close方法。像数据库链接类Connection,io类InputStream或OutputStream都直接或者间接实现了该接口
     * 
     * <p>
     * 
     */
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
