package io.github.kavahub.learnjava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 读取文件，计算行数
 */
@Slf4j
@UtilityClass
public class NumberOfLineFinder {
    public int getTotalNumberOfLinesUsingBufferedReader(String fileName) {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) {
                lines++;
            }
        } catch (IOException e) {
            log.error("文件读取异常", e);
        }
        return lines;
    }

    public int getTotalNumberOfLinesUsingLineNumberReader(String fileName) {
        int lines = 0;
        try (LineNumberReader reader = new LineNumberReader(new FileReader(fileName))) {
            reader.skip(Integer.MAX_VALUE);
            lines = reader.getLineNumber() + 1;
        } catch (IOException e) {
            log.error("文件读取异常", e);
        }
        return lines;
    }

    public int getTotalNumberOfLinesUsingScanner(String fileName) {
        int lines = 0;
        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                lines++;
            }
        } catch (IOException e) {
            log.error("文件读取异常", e);
        }
        return lines;
    }

    public int getTotalNumberOfLinesUsingNIOFiles(String fileName) {
        int lines = 0;
        try (Stream<String> fileStream = Files.lines(Paths.get(fileName))) {
            lines = (int) fileStream.count();
        } catch (IOException e) {
            log.error("文件读取异常", e);
        }
        return lines;
    }

    public int getTotalNumberOfLinesUsingNIOFilesReadAllLines(String fileName) {
        int lines = 0;
        try {
            List<String> fileStream = Files.readAllLines(Paths.get(fileName));
            lines = fileStream.size();
        } catch (IOException e) {
            log.error("文件读取异常", e);
        }
        return lines;
    }

    public int getTotalNumberOfLinesUsingNIOFileChannel(String fileName) {
        int lines = 1;
        try (FileChannel channel = FileChannel.open(Paths.get(fileName), StandardOpenOption.READ)) {
            ByteBuffer byteBuffer = channel.map(MapMode.READ_ONLY, 0, channel.size());
            while (byteBuffer.hasRemaining()) {
                byte currentChar = byteBuffer.get();
                if (currentChar == '\n') {
                    lines++;
                }
            }
        } catch (IOException e) {
            log.error("文件读取异常", e);
        }
        return lines;
    }

    public int getTotalNumberOfLinesUsingApacheCommonsIO(String fileName) {
        int lines = 0;
        try {
            LineIterator lineIterator = FileUtils.lineIterator(new File(fileName));
            while (lineIterator.hasNext()) {
                lineIterator.nextLine();
                lines++;
            }
        } catch (IOException e) {
            log.error("文件读取异常", e);
        }
        return lines;
    }

    public int getTotalNumberOfLinesUsingGoogleGuava(String fileName) {
        int lines = 0;
        try {
            List<String> lineItems = com.google.common.io.Files.readLines(Paths.get(fileName)
                .toFile(), Charset.defaultCharset());
            lines = lineItems.size();
        } catch (IOException e) {
            log.error("文件读取异常", e);
        }
        return lines;
    }
       
}
