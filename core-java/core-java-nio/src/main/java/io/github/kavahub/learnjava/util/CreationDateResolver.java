package io.github.kavahub.learnjava.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Optional;

import lombok.experimental.UtilityClass;

/**
 * 
 * 文件创建时间属性
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class CreationDateResolver {
    public Instant resolveCreationTimeWithBasicAttributes(Path path) {
        try {
            final BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            final FileTime fileTime = attr.creationTime();

            return fileTime.toInstant();
        } catch (IOException ex) {
            throw new RuntimeException("An issue occured went wrong when resolving creation time", ex);
        }
    }

    public Optional<Instant> resolveCreationTimeWithAttribute(Path path) {
        try {
            final FileTime creationTime = (FileTime) Files.getAttribute(path, "creationTime");

            return Optional
              .ofNullable(creationTime)
              .map((FileTime::toInstant));
        } catch (IOException ex) {
            throw new RuntimeException("An issue occured went wrong when resolving creation time", ex);
        }
    }        
}
