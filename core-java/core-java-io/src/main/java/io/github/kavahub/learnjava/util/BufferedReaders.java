package io.github.kavahub.learnjava.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

/**
 * 
 * {@link BufferedReader} 工具
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class BufferedReaders {
    /**
     * 读取所有行，使用 {@link BufferedReader#readLine()} 逐行读取
     * 
     * @param reader
     * @return
     * @throws IOException
     */
    public String readAllLines(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }

        return content.toString();
    }

    /**
     * 读取所有行， 使用 {@link BufferedReader#lines()} 读取
     * @param reader
     * @return
     */
    public String readAllLinesWithStream(BufferedReader reader) {
        return reader
                .lines()
                    .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * 一个字符一个字符读取， 使用 {@link BufferedReader#read()} 读取
     * @param reader
     * @return
     * @throws IOException
     */
    public String readAllCharsOneByOne(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();

        int value;
        while ((value = reader.read()) != -1) {
            content.append((char) value);
        }

        return content.toString();
    }

    /**
     * 一次读取多个字符， 使用 {@link BufferedReader#read(char[], int, int)} 读取
     * @param reader
     * @return
     * @throws IOException
     */
    public String readMultipleChars(BufferedReader reader) throws IOException {
        // 读取5个字符
        int length = 5;
        char[] chars = new char[length];
        int charsRead = reader.read(chars, 0, length);

        String result;
        if (charsRead != -1) {
            result = new String(chars, 0,  charsRead);
        } else {
            result = "";
        }

        return result;
    }

    /**
     * 读取文件
     * 
     * @param file
     * @return
     */
    public String readFile(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String content = readAllLines(reader);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件
     * 
     * @param file
     * @return
     */
    public String readFileTryWithResources(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String content = readAllLines(reader);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }   
}
