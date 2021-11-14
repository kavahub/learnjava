package io.github.kavahub.learnjava.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class AccessingAllClassesInPackageTest {
    // @Rule
    // public final ExpectedException exception = ExpectedException.none();

    private static final String PACKAGE_NAME = "io.github.kavahub.learnjava.reflect.demo";

    @Test
    public void when_findAllClassesUsingClassLoader_thenSuccess() {
        AccessingAllClassesInPackage instance = new AccessingAllClassesInPackage();
        Set<Class<?>> classes = instance.findAllClassesUsingClassLoader(PACKAGE_NAME);
        assertEquals(5, classes.size());
    }

    @Test
    public void when_findAllClassesUsingReflectionsLibrary_thenSuccess() {
        AccessingAllClassesInPackage instance = new AccessingAllClassesInPackage();
        Set<Class<?>> classes = instance.findAllClassesUsingReflectionsLibrary(PACKAGE_NAME);
        assertEquals(5, classes.size());
    }

    @Test
    public void when_findAllClassesUsingGoogleGuice_thenSuccess() throws IOException {
        AccessingAllClassesInPackage instance = new AccessingAllClassesInPackage();
        Set<Class<?>> classes = instance.findAllClassesUsingGoogleGuice(PACKAGE_NAME);
        assertEquals(5, classes.size());
    }
}
