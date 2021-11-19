package io.github.kavahub.learnjava;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InterfacesDefaultTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    void setup() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void givenACustomFooObject_whenCallingDefaultMethodBar_thenExpectedStringIsWrittenToSystemOut() {
        CustomFoo customFoo = new CustomFoo();
        customFoo.bar();
        Assertions.assertThat(outContent.toString()).isEqualTo("Hello world!");
    }

    @Test
    void givenAFooInterface_whenCallingStaticMethodBuzz_thenExpectedStringIsWrittenToSystemOut() {
        Foo.buzz();
        Assertions.assertThat(outContent.toString()).isEqualTo("Hello static world!");
    }  
    
    public static class CustomFoo implements Foo {

        // 这个main不能运行：找不到或无法加载主类 io.github.kavahub.learnjava.InterfacesDefaultTest$CustomFoo
        public static void main(String... args) {
            Foo customFoo = new CustomFoo();
            customFoo.bar(); // 'Hello world!'
            Foo.buzz(); // 'Hello static world!'
        }
    }

    public static interface Foo {

        public default void bar() {
            System.out.print("Hello");
            baz();
        }
    
        public static void buzz() {
            System.out.print("Hello");
            staticBaz();
        }
    
        private void baz() {
            System.out.print(" world!");
        }
    
        private static void staticBaz() {
            System.out.print(" static world!");
        }
    }
}
