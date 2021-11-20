package io.github.kavahub.learnjava.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.github.kavahub.learnjava.reflect.AccessingAllClassesInExample.*;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class AccessingAllClassesInPackageTest {
    // @Rule
    // public final ExpectedException exception = ExpectedException.none();

    private static final String PACKAGE_NAME = "io.github.kavahub.learnjava.reflect.example";

    @Test
    public void when_findAllClassesUsingClassLoader_thenSuccess() {
        Set<Class<?>> classes = findAllClassesUsingClassLoader(PACKAGE_NAME);
        assertEquals(5, classes.size());
    }

    @Test
    public void when_findAllClassesUsingReflectionsLibrary_thenSuccess() {
        Set<Class<?>> classes = findAllClassesUsingReflectionsLibrary(PACKAGE_NAME);
        assertEquals(5, classes.size());
    }

    @Test
    public void when_findAllClassesUsingGoogleGuice_thenSuccess() throws IOException {
        Set<Class<?>> classes = findAllClassesUsingGoogleGuice(PACKAGE_NAME);
        assertEquals(5, classes.size());
    }
}
