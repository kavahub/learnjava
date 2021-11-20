package io.github.kavahub.learnjava.enhance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

public class CustomClassLoaderTest {
    @Test
    public void customLoader() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        CustomClassLoader customClassLoader = new CustomClassLoader();
        Class<?> c = customClassLoader.findClass(Example.class.getName());

        Object ob = c.getDeclaredConstructor().newInstance();

        Method md = c.getMethod("print");
        md.invoke(ob);

    }   

    public static class Example {
        public void print() {
            System.out.println("Print started.");
        } 
    }
}
