package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static  io.github.kavahub.learnjava.util.FileLister.*;

public class FileListerTest {
    private String DIRECTORY = "src/test/resources/listFilesUnitTestFolder";
    private static final int DEPTH = 1;
    private Set<String> EXPECTED_FILE_LIST = new HashSet<String>() {
        {
            add("test.xml");
            add("employee.json");
            add("students.json");
            add("country.txt");
        }
    };

    @Test
    public void givenDir_whenUsingJAVAIO_thenListAllFiles() {
        assertEquals(EXPECTED_FILE_LIST, listFilesUsingJavaIO(DIRECTORY));
    }

    @Test
    public void givenDir_whenUsingFilesList_thenListAllFiles() throws IOException {
        assertEquals(EXPECTED_FILE_LIST, listFilesUsingFilesList(DIRECTORY));
    }

    @Test
    public void givenDir_whenWalkingTree_thenListAllFiles() throws IOException {
        assertEquals(EXPECTED_FILE_LIST, listFilesUsingFileWalk(DIRECTORY,DEPTH));
    }

    @Test
    public void givenDir_whenWalkingTreeWithVisitor_thenListAllFiles() throws IOException {
        Set<String> actualSet = listFilesUsingFileWalkAndVisitor(DIRECTORY);
        actualSet.removeAll(EXPECTED_FILE_LIST);
        
        assertEquals("simple.txt", actualSet.iterator().next());
    }

    @Test
    public void givenDir_whenUsingDirectoryStream_thenListAllFiles() throws IOException {
        assertEquals(EXPECTED_FILE_LIST, listFilesUsingDirectoryStream(DIRECTORY));
    }    
}
