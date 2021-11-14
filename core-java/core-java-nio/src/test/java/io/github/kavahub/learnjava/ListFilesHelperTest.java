package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class ListFilesHelperTest {
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
        assertEquals(EXPECTED_FILE_LIST, ListFilesHelper.listFilesUsingJavaIO(DIRECTORY));
    }

    @Test
    public void givenDir_whenUsingFilesList_thenListAllFiles() throws IOException {
        assertEquals(EXPECTED_FILE_LIST, ListFilesHelper.listFilesUsingFilesList(DIRECTORY));
    }

    @Test
    public void givenDir_whenWalkingTree_thenListAllFiles() throws IOException {
        assertEquals(EXPECTED_FILE_LIST, ListFilesHelper.listFilesUsingFileWalk(DIRECTORY,DEPTH));
    }

    @Test
    public void givenDir_whenWalkingTreeWithVisitor_thenListAllFiles() throws IOException {
        Set<String> actualSet = ListFilesHelper.listFilesUsingFileWalkAndVisitor(DIRECTORY);
        actualSet.removeAll(EXPECTED_FILE_LIST);
        
        assertEquals("simple.txt", actualSet.iterator().next());
    }

    @Test
    public void givenDir_whenUsingDirectoryStream_thenListAllFiles() throws IOException {
        assertEquals(EXPECTED_FILE_LIST, ListFilesHelper.listFilesUsingDirectoryStream(DIRECTORY));
    }    
}
