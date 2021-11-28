package io.github.kavahub.learnjava.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import lombok.experimental.UtilityClass;

/**
 * 文件写入
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
@UtilityClass
public class FileOutputStreamWriter {
    /**
     * 写入数据
     * 
     * @param file
     * @param data
     * @throws IOException
     */
    public void fileOutputStreamByteSequence(String file, String data) throws IOException {
        byte[] bytes = data.getBytes();
        try (OutputStream out = new FileOutputStream(file)) {
            out.write(bytes);
        }
    }

    /**
     * 写入部分数据
     * @param file
     * @param data
     * @throws IOException
     */
    public void fileOutputStreamByteSubSequence(String file, String data) throws IOException {
        byte[] bytes = data.getBytes();
        try (OutputStream out = new FileOutputStream(file)) {
            // 写入数据的第6个字节，长度5的数据
            out.write(bytes, 6, 5);
        }
    }

    /**
     * 写入一个字节
     * @param file
     * @param data
     * @throws IOException
     */
    public void fileOutputStreamByteSingle(String file, String data) throws IOException {
        byte[] bytes = data.getBytes();
        try (OutputStream out = new FileOutputStream(file)) {
            out.write(bytes[6]);
        }
    }

    /**
     * 使用BufferedOutputStream
     * @param file
     * @param data
     * @throws IOException
     */
    public void bufferedOutputStream(String file, String... data) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            for (String s : data) {
                out.write(s.getBytes());
                out.write(" ".getBytes());
            }
        }
    }

    /**
     * 使用Writer
     * 
     * @param file
     * @param data
     * @throws IOException
     */
    public void outputStreamWriter(String file, String data) throws IOException {
        try (OutputStream out = new FileOutputStream(file); Writer writer = new OutputStreamWriter(out, "UTF-8")) {
            writer.write(data);
        }
    }
}
