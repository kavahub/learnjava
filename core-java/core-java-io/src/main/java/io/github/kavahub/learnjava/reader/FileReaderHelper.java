package io.github.kavahub.learnjava.reader;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileReaderHelper {
    public String readAllCharactersOneByOne(Reader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        int nextChar;
        while ((nextChar = reader.read()) != -1) {
            content.append((char) nextChar);
        }
        return String.valueOf(content);
    }

    public String readMultipleCharacters(Reader reader, int length) throws IOException {
        char[] buffer = new char[length];
        int charactersRead = reader.read(buffer, 0, length);


        if (charactersRead != -1) {
            return new String(buffer, 0, charactersRead);
        } else {
            return "";
        }
    }

    public String readFile(String path) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(path);
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

    public String readFileUsingTryWithResources(String path) {
        try (FileReader fileReader = new FileReader(path)) {
            return readAllCharactersOneByOne(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }  
}
