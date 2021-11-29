package io.github.kavahub.learnjava.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

import lombok.experimental.UtilityClass;

/**
 * 
 * 字符编码
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class CharacterEncoding {
 
    /**
     * 使用指定的编码读取文件
     * 
     * @param filePath
     * @param encoding
     * @return
     * @throws IOException
     */
    public String readFile(String filePath, String encoding) throws IOException {
        File file = new File(filePath);
        StringBuffer buffer = new StringBuffer();
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(file), encoding)) {
            int data;
            while ((data = isr.read()) != -1) {
                buffer.append((char) data);
            }
        }
        return buffer.toString();
    }

    /**
     * 指定字符串及编码，转换成二进制
     * 
     * @param input
     * @param encoding
     * @return
     * @throws UnsupportedEncodingException
     */
    public String convertToBinary(String input, String encoding) throws UnsupportedEncodingException {
        byte[] bytes = input.getBytes(encoding);
        StringBuffer buffer = new StringBuffer();
        for (int b : bytes) {
            buffer.append(Integer.toBinaryString((b + 256) % 256));
            buffer.append(" ");
        }
        return buffer.toString();
    }

    /**
     * 使用指定的编码字符串
     * 
     * @param input
     * @param charset
     * @param codingErrorAction
     * @return
     * @throws IOException
     */
    public String decodeText(String input, Charset charset, CodingErrorAction codingErrorAction) throws IOException {
        CharsetDecoder charsetDecoder = charset.newDecoder();
        charsetDecoder.onMalformedInput(codingErrorAction);
        return new BufferedReader(
          new InputStreamReader(
            new ByteArrayInputStream(input.getBytes()), charsetDecoder)).readLine();
    }   
}
