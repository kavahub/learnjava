package io.github.kavahub.learnjava;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WriteCsvFileHelper {
    public String convertToCSV(String[] data) {
        return Stream.of(data)
            .map(WriteCsvFileHelper::escapeSpecialCharacters)
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
