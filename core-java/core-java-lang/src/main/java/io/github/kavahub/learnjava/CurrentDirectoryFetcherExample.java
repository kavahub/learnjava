package io.github.kavahub.learnjava;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

/**
 * 获取当前工作路径
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CurrentDirectoryFetcherExample {
    public static void main(String[] args) {
        System.out.printf("Current Directory Using Java System API: %s%n", currentDirectoryUsingSystemProperties());

        System.out.printf("Current Directory Using Java IO File API: %s%n", currentDirectoryUsingFile());

        System.out.printf("Current Directory Using Java NIO FileSystems API: %s%n", currentDirectoryUsingFileSystems());

        System.out.printf("Current Directory Using Java NIO Paths API: %s%n", currentDirectoryUsingPaths());
    }

    public static String currentDirectoryUsingSystemProperties() {
        return System.getProperty("user.dir");
    }

    public static String currentDirectoryUsingPaths() {
        return Paths.get("")
            .toAbsolutePath()
            .toString();
    }

    public static String currentDirectoryUsingFileSystems() {
        return FileSystems.getDefault()
            .getPath("")
            .toAbsolutePath()
            .toString();
    }

    public static String currentDirectoryUsingFile() {
        return new File("").getAbsolutePath();
    }   
}
