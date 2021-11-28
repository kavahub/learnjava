package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.io.Files;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

/**
 * 获取文件名，不带扩展名
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
public class FileNameDelExtensionTest {
    @Test
    public void givenDotFileWithoutExt_whenCallGuavaMethod_thenCannotGetDesiredResult() {
        assertEquals("", Files.getNameWithoutExtension(".learnjava"));
    }

    @Test
    public void givenFileWithoutMultipleExt_whenCallGuavaMethod_thenCannotRemoveAllExtensions() {
        assertEquals("learnjava.tar", Files.getNameWithoutExtension("learnjava.tar.gz"));
    }

    @Test
    public void givenDotFileWithoutExt_whenCallApacheCommonsMethod_thenCannotGetDesiredResult() {
        assertEquals("", FilenameUtils.removeExtension(".learnjava"));
    }

    @Test
    public void givenFileWithoutMultipleExt_whenCallApacheCommonsMethod_thenCannotRemoveAllExtensions() {
        assertEquals("learnjava.tar", FilenameUtils.removeExtension("learnjava.tar.gz"));
    }

    @Test
    public void givenFilenameNoExt_whenCallFilenameUtilMethod_thenGetExpectedFilename() {
        assertEquals("learnjava", removeFileExtension("learnjava", true));
        assertEquals("learnjava", removeFileExtension("learnjava", false));
    }

    @Test
    public void givenSingleExt_whenCallFilenameUtilMethod_thenGetExpectedFilename() {
        assertEquals("learnjava", removeFileExtension("learnjava.txt", true));
        assertEquals("learnjava", removeFileExtension("learnjava.txt", false));
    }


    @Test
    public void givenDotFile_whenCallFilenameUtilMethod_thenGetExpectedFilename() {
        assertEquals(".learnjava", removeFileExtension(".learnjava", true));
        assertEquals(".learnjava", removeFileExtension(".learnjava", false));
    }

    @Test
    public void givenDotFileWithExt_whenCallFilenameUtilMethod_thenGetExpectedFilename() {
        assertEquals(".learnjava", removeFileExtension(".learnjava.conf", true));
        assertEquals(".learnjava", removeFileExtension(".learnjava.conf", false));
    }

    @Test
    public void givenDoubleExt_whenCallFilenameUtilMethod_thenGetExpectedFilename() {
        assertEquals("learnjava", removeFileExtension("learnjava.tar.gz", true));
        assertEquals("learnjava.tar", removeFileExtension("learnjava.tar.gz", false));
    }

    @Test
    public void givenDotFileWithDoubleExt_whenCallFilenameUtilMethod_thenGetExpectedFilename() {
        assertEquals(".learnjava", removeFileExtension(".learnjava.conf.bak", true));
        assertEquals(".learnjava.conf", removeFileExtension(".learnjava.conf.bak", false));
    }

    public String removeFileExtension(String filename, boolean removeAllExtensions) {
        if (filename == null || filename.isEmpty()) {
            return filename;
        }

        String extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
        return filename.replaceAll(extPattern, "");
    }
}
