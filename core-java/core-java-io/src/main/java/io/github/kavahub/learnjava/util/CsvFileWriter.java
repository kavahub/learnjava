package io.github.kavahub.learnjava.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;

/**
 * csv文件写入
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
@UtilityClass
public class CsvFileWriter {
    public String convertToCSV(String[] data) {
        return Stream.of(data)
            .map(CsvFileWriter::escapeSpecialCharacters)
            .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
