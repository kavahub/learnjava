package io.github.kavahub.learnjava.reflect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.reflect.ClassPath;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessingAllClassesInPackage {
    public Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines().filter(line -> line.endsWith(".class")).map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            log.error("<<Class not found>>");
        }
        return null;
    }

    public Set<Class<?>> findAllClassesUsingReflectionsLibrary(String packageName) {
        // 0.9.12
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class);

        // 0.10.1
        // Reflections reflections = new Reflections(new ConfigurationBuilder()
        // .forPackage(packageName)
        // .addScanners(Scanners.SubTypes));

        // return reflections.getSubTypesOf(Object.class);
    }

    public Set<Class<?>> findAllClassesUsingGoogleGuice(String packageName) throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader()).getAllClasses().stream()
                .filter(clazz -> clazz.getPackageName().equalsIgnoreCase(packageName)).map(clazz -> clazz.load())
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        System.out.println(
            new AccessingAllClassesInPackage().findAllClassesUsingReflectionsLibrary("net.learnjava.reflect.demo"));
    }
}
