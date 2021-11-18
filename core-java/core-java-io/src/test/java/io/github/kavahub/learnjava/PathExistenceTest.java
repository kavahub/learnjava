package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

public class PathExistenceTest {
    @Test
    public void givenFile_whenDoesNotExist_thenFilesReturnsFalse() {
        Path path = Paths.get("does-not-exist.txt");

        assertFalse(Files.exists(path));
        assertTrue(Files.notExists(path));
    }

    @Test
    public void givenFile_whenExists_thenFilesShouldReturnTrue() throws IOException {
        Path tempFile = Files.createTempFile("learnjava", "exist-nio");
        assertTrue(Files.exists(tempFile));

        Path tempDirectory = Files.createTempDirectory("existsDir");
        assertTrue(Files.exists(tempDirectory));

        assertTrue(Files.isDirectory(tempDirectory));
        assertFalse(Files.isDirectory(tempFile));
        assertTrue(Files.isRegularFile(tempFile));

        assertTrue(Files.isReadable(tempFile));

        //Files.deleteIfExists(tempFile);
        //Files.deleteIfExists(tempDirectory);
    }

    @Test
    public void givenSymbolicLink_whenTargetDoesNotExists_thenFollowOrNotBasedOnTheOptions() throws IOException {
        Path target = Files.createTempFile("learnjava", "target");
        Path symbol = Paths.get("test-link-" + ThreadLocalRandom.current().nextInt());
        Path symbolicLink = null;

        try {
            symbolicLink = Files.createSymbolicLink(symbol, target);
        } catch (FileSystemException ex) {
            System.out.println("Your OS security policy prevents the current user from creating symbolic links.\n" +
                    "Most probably you're running Windows with UAC.\n" +
                    "If this is the case, please see - https://docs.microsoft.com/en-us/windows/security/threat-protection/security-policy-settings/create-symbolic-links\n" +
                    "You must change your security settings to run this test under Windows.");
            return;
        }

        assertTrue(Files.exists(symbolicLink));
        assertTrue(Files.isSymbolicLink(symbolicLink));
        assertFalse(Files.isSymbolicLink(target));

        Files.deleteIfExists(target);
        assertFalse(Files.exists(symbolicLink));
        assertTrue(Files.exists(symbolicLink, LinkOption.NOFOLLOW_LINKS));

        Files.deleteIfExists(symbolicLink);
    }

    @Test
    public void givenFile_whenDoesNotExist_thenFileReturnsFalse() {
        assertFalse(new File("invalid").exists());
        assertFalse(new File("invalid").isFile());
    }

    @Test
    public void givenFile_whenExist_thenShouldReturnTrue() throws IOException {
        Path tempFilePath = Files.createTempFile("learnjava", "exist-io");
        Path tempDirectoryPath = Files.createTempDirectory("learnjava-exists-io");

        File tempFile = new File(tempFilePath.toString());
        File tempDirectory = new File(tempDirectoryPath.toString());

        assertTrue(tempFile.exists());
        assertTrue(tempDirectory.exists());

        assertTrue(tempFile.isFile());
        assertFalse(tempDirectory.isFile());

        assertTrue(tempDirectory.isDirectory());
        assertFalse(tempFile.isDirectory());

        assertTrue(tempFile.canRead());

        //Files.deleteIfExists(tempFilePath);
        //Files.deleteIfExists(tempDirectoryPath);
    }    
}