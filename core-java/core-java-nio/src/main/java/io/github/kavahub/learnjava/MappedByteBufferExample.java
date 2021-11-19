package io.github.kavahub.learnjava;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

/**
 * <ul>
 * <li>MappedByteBuffer使用虚拟内存，因此分配(map)的内存大小不受JVM的-Xmx参数限制，但是也是有大小限制的。</li>
 * <li>如果当文件超出1.5G限制时，可以通过position参数重新map文件后面的内容。</li>
 * <li>MappedByteBuffer在处理大文件时的确性能很高，但也存在一些问题，如内存占用、文件关闭不确定，
 * 被其打开的文件只有在垃圾回收的才会被关闭，而且这个时间点是不确定的。javadoc中也提到： A mapped byte buffer and the
 * file mapping that it represents remain valid until the buffer itself is
 * garbage-collected.</li>
 */
public class MappedByteBufferExample {
    private final static int length = 1024 * 1024 * 5; // 5M
    private final static String FILE_NAME = "MappedByteBufferExample.txt";

    private abstract static class Worker {
        private String name;

        public Worker(String name) {
            this.name = name;
        }

        public void run() {
            System.out.print(name + ": ");
            long start = System.currentTimeMillis();
            doRun();
            System.out.println(System.currentTimeMillis() - start + " ms");
        }

        public abstract void doRun();
    }

    private static Worker[] workers = { new Worker("Stream Write") {
        public void doRun() {
            try {
                Files.deleteIfExists(Paths.get(FILE_NAME));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
                    DataOutputStream dos = new DataOutputStream(fos);) {

                byte b = (byte) 0;
                for (int i = 0; i < length; i++) {
                    dos.writeByte(b);
                }
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }, new Worker("Stream Read") {
        public void doRun() {
            try (FileInputStream fis = new FileInputStream(FILE_NAME); DataInputStream dis = new DataInputStream(fis)) {

                while (dis.read() != -1) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }, new Worker("Mapped Write") {
        public void doRun() {
            try {
                Files.deleteIfExists(Paths.get(FILE_NAME));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try (FileChannel channel = (FileChannel) Files.newByteChannel(Paths.get(FILE_NAME),
                    EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE))) {
                MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
                for (int i = 0; i < length; i++) {
                    mapBuffer.put((byte) 0);
                }
                mapBuffer.force();
                mapBuffer = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }, new Worker("Mapped Read") {
        public void doRun() {
            try (FileChannel channel = (FileChannel) Files.newByteChannel(Paths.get(FILE_NAME),
                    EnumSet.of(StandardOpenOption.READ))) {
                MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);
                while (mapBuffer.hasRemaining()) {
                    mapBuffer.get();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }, new Worker("Mapped PRIVATE Write") {
        public void doRun() {
            // 只有在垃圾回收的才会被关闭，否则删除时异常java.nio.file.AccessDeniedException
            System.gc();
            try {
                Files.deleteIfExists(Paths.get(FILE_NAME));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try (FileChannel channel = (FileChannel) Files.newByteChannel(Paths.get(FILE_NAME),
                    EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE))) {
                MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.PRIVATE, 0, length);
                for (int i = 0; i < length; i++) {
                    mapBuffer.put((byte) 0);
                }
                mapBuffer.force();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }, new Worker("Mapped PRIVATE Read") {
        public void doRun() {
            try (FileChannel channel = (FileChannel) Files.newByteChannel(Paths.get(FILE_NAME),
                    EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE))) {
                MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.PRIVATE, 0, length);
                while (mapBuffer.hasRemaining()) {
                    mapBuffer.get();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } };

    public static void main(String[] args) throws IOException, InterruptedException {
        for (Worker work : workers) {
            TimeUnit.SECONDS.sleep(1);
            work.run();
        }
    }
}
