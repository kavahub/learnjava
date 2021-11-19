package io.github.kavahub.learnjava;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件查找示例
 */
@Slf4j
public class FileSearchExample implements FileVisitor<Path> {
    private final String fileName;
    private final Path startDir;

    public FileSearchExample(String fileName, Path startingDir) {
        this.fileName = fileName;
        startDir = startingDir;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    /**
     * 访问文件时
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String fileName = file.getFileName().toString();
        if (this.fileName.equals(fileName)) {
            log.info("File found: " + file.toString());
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
    }

    /**
     * 访问文件失败时
     */
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        log.info("Failed to access file: " + file.toString());
        return FileVisitResult.CONTINUE;
    }

    /**
     * 访问目录时
     */
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        boolean finishedSearch = Files.isSameFile(dir, startDir);
        if (finishedSearch) {
            log.info("File:" + fileName + " not found");
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws IOException {
        Path startingDir = Paths.get(System.getProperty("user.home"));
        FileSearchExample crawler = new FileSearchExample(".gitconfig", startingDir);
        Files.walkFileTree(startingDir, crawler);
    }  
    
}
