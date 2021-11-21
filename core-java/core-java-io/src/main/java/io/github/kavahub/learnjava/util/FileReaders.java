package io.github.kavahub.learnjava.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import lombok.experimental.UtilityClass;

/**
 * {@link FileReader} 工具
 */
@UtilityClass
public class FileReaders {
    /**
     * 读取所有字符，一个一个的读，使用 {@link Reader#read()} 方法 
     * 
     * @param reader
     * @return
     * @throws IOException
     */
    public String readAllCharactersOneByOne(Reader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        int nextChar;
        while ((nextChar = reader.read()) != -1) {
            content.append((char) nextChar);
        }
        return String.valueOf(content);
    }

    /**
     * 读取多个字符，使用 {@link Reader#read(char[], int, int)} 方法 
     * 
     * @param reader
     * @param length
     * @return
     * @throws IOException
     */
    public String readMultipleCharacters(Reader reader, int length) throws IOException {
        char[] buffer = new char[length];
        int charactersRead = reader.read(buffer, 0, length);


        if (charactersRead != -1) {
            return new String(buffer, 0, charactersRead);
        } else {
            return "";
        }
    }

    /**
     * 读取文件
     * 
     * @param file
     * @return
     */
    public String readFile(File file) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            return readAllCharactersOneByOne(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 读取文件
     * 
     * @param file
     * @return
     */
    public String readFileUsingTryWithResources(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            return readAllCharactersOneByOne(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }  
}
