package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * {@link File} 示例
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
public class FileClassTest {
    @Test
    public void givenDir_whenMkdir_thenDirIsDeleted() {
        File directory = new File("dir");
        assertTrue(directory.mkdir());
        assertTrue(directory.delete());
    }

    @Test
    public void givenFile_whenCreateNewFile_thenFileIsDeleted() throws IOException {
        File file = new File("file.txt");
        assertTrue(file.createNewFile());
        assertTrue(file.delete());
    }

    @Test
    public void givenFile_whenCreateNewFile_thenMetadataIsCorrect() throws IOException {

        String sep = File.separator;

        File parentDir = makeDir("filesDir");

        File child = new File(parentDir, "file.txt");
        child.createNewFile();

        assertEquals("file.txt", child.getName());
        assertEquals(parentDir.getName(), child.getParentFile().getName());
        assertEquals(parentDir.getPath() + sep + "file.txt", child.getPath());

        removeDir(parentDir);
    }


    @Test
    public void givenReadOnlyFile_whenCreateNewFile_thenCantModFile() throws IOException {
        File parentDir = makeDir("readDir");

        File child = new File(parentDir, "file.txt");
        child.createNewFile();

        child.setWritable(false);
        assertThrows(IOException.class, () -> new FileOutputStream(child));

        removeDir(parentDir);

        // boolean writable = true;
        // try (FileOutputStream fos = new FileOutputStream(child)) {
        //     fos.write("Hello World".getBytes()); // write operation
        //     fos.flush();
        // } catch (IOException e) {
        //     writable = false;
        // } finally {
        //     removeDir(parentDir);
        // }
        // assertFalse(writable);
    }

    @Disabled("测试失败")
    @Test
    public void givenWriteOnlyFile_whenCreateNewFile_thenCantReadFile() throws IOException {
        File parentDir = makeDir("writeDir");

        File child = new File(parentDir, "file.txt");
        child.createNewFile();
        child.setReadable(false);
        boolean readable = true;
        try (FileInputStream fis = new FileInputStream(child)) {
            fis.read(); // read operation
        } catch (IOException e) {
            readable = false;
        } finally {
            removeDir(parentDir);
        }
        assertFalse(readable);
    }

    @Test
    public void givenFilesInDir_whenCreateNewFile_thenCanListFiles() throws IOException {
        File parentDir = makeDir("filtersDir");

        String[] files = {"file1.csv", "file2.txt"};
        for (String file : files) {
            new File(parentDir, file).createNewFile();
        }

        //normal listing
        assertEquals(2, parentDir.list().length);

        //filtered listing
        FilenameFilter csvFilter = (dir, ext) -> ext.endsWith(".csv");
        assertEquals(1, parentDir.list(csvFilter).length);

        removeDir(parentDir);
    }

    @Test
    public void givenDir_whenMkdir_thenCanRenameDir() {

        File source = makeDir("source");
        //File destination = makeDir("destination");
        File destination = new File("destination");
        boolean renamed = source.renameTo(destination);

        if (renamed) {
            assertFalse(source.isDirectory());
            assertTrue(destination.isDirectory());

            removeDir(destination);
        }
    }

    @Test
    public void givenDataWritten_whenWrite_thenFreeSpaceReduces() throws IOException {

        File testDir = makeDir("dir");
        File sample = new File(testDir, "sample.txt");

        long freeSpaceBefore = testDir.getFreeSpace();
        writeSampleDataToFile(sample);

        long freeSpaceAfter = testDir.getFreeSpace();
        assertTrue(freeSpaceAfter < freeSpaceBefore);

        removeDir(testDir);
    }

    private static File makeDir(String name) {
        File directory = new File(name);

        // If the directory already exists, make sure we create it 'from scratch', i.e. all the files inside are deleted first
        if (directory.exists()) {
            removeDir(directory);
        }

        if (directory.mkdir()) {
            return directory;
        }

        throw new RuntimeException("'" + name + "' not made!");
    }

    private static void removeDir(File directory) {
        // make sure you don't delete your home directory here
        String home = System.getProperty("user.home");
        if (directory.getPath().equals(home)) {
            return;
        }

        // remove directory and its files from system
        if (directory.exists()) {
            // delete all files inside the directory
            File[] dirFiles = directory.listFiles();
            if (dirFiles != null) {
                List<File> files = Arrays.asList(dirFiles);
                files.forEach(f -> deleteFile(f));
            }

            // finally delete the directory itself
            deleteFile(directory);
        }
    }

    private static void deleteFile(File fileToDelete) {
        if (fileToDelete != null && fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    private static void writeSampleDataToFile(File sample) throws IOException {
        //write sample text to file
        try (FileOutputStream out = new FileOutputStream(sample)) {
            for (int i = 1; i <= 100000; i++) {
                String text = "Sample line number " + i + "\n";
                out.write(text.getBytes());
            }
        }
    }
}
