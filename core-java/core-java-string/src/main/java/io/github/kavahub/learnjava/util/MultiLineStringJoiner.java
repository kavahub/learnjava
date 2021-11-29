package io.github.kavahub.learnjava.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import lombok.experimental.UtilityClass;


/**
 * 
 * 多行字符串 合并
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class MultiLineStringJoiner {
    String newLine = System.getProperty("line.separator");
    
    public String stringConcatenation() {
        return "Get busy living"
                .concat(newLine)
                .concat("or")
                .concat(newLine)
                .concat("get busy dying.")
                .concat(newLine)
                .concat("--Stephen King");
    }
    
    public String stringJoin() {
        return String.join(newLine,
                           "Get busy living",
                           "or",
                           "get busy dying.",
                           "--Stephen King");
    }
    
    public String stringBuilder() {
        return new StringBuilder()
                .append("Get busy living")
                .append(newLine)
                .append("or")
                .append(newLine)
                .append("get busy dying.")
                .append(newLine)
                .append("--Stephen King")
                .toString();
    }
    
    public String stringWriter() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("Get busy living");
        printWriter.println("or");
        printWriter.println("get busy dying.");
        printWriter.println("--Stephen King");
        return stringWriter.toString();
    }
    
    public String guavaJoiner() {
        return Joiner.on(newLine).join(ImmutableList.of("Get busy living",
            "or",
            "get busy dying.",
            "--Stephen King"));
    }
    
    public String loadFromFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/main/resources/stephenking.txt")));
    }

    // jdk 15
    // public String textBlocks() {
    //     return """
    //         Get busy living
    //         or
    //         get busy dying.
    //         --Stephen King""";
    // }    
}
