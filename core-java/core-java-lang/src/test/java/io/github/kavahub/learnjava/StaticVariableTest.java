package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

/**
 * 静态变量
 */
public class StaticVariableTest {
    @Test
    public void initializeStaticVariable_checkAssignedValues() throws ClassNotFoundException, NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {
        Class<?> staticVariableDemo = this.getClass().getClassLoader()
                .loadClass("io.github.kavahub.learnjava.StaticVariableTest$StaticVariableDemo");

        Field field1 = staticVariableDemo.getField("i");

        assertThat(field1.getInt(staticVariableDemo)).isEqualTo(0);

        Field field2 = staticVariableDemo.getField("j");

        assertThat(field2.getInt(staticVariableDemo)).isEqualTo(20);

    }

    @Test
    public void initializeStaticVariable_checkStaticBlock() throws ClassNotFoundException, NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {

        Class<?> staticVariableDemo = this.getClass().getClassLoader()
                .loadClass("io.github.kavahub.learnjava.StaticVariableTest$StaticVariableDemo");

        Field field1 = staticVariableDemo.getField("z");

        assertThat(field1.getInt(staticVariableDemo)).isEqualTo(30);

        Field field2 = staticVariableDemo.getField("a");

        assertThat(field2.getInt(staticVariableDemo)).isEqualTo(50);

    }

    @Test
    public void initializeStaticVariable_checkFinalValues() throws ClassNotFoundException, IllegalArgumentException,
            IllegalAccessException, NoSuchFieldException, SecurityException {
        Class<?> staticVariableDemo = this.getClass().getClassLoader()
                .loadClass("io.github.kavahub.learnjava.StaticVariableTest$StaticVariableDemo");

        Field field1 = staticVariableDemo.getField("b");

        assertThat(field1.getInt(staticVariableDemo)).isEqualTo(100);

    }

    @Test
    public void initializeStaticVariable_checkInnerClassValues() throws ClassNotFoundException, NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {

        Class<?> staticVariableDemo = this.getClass().getClassLoader()
                .loadClass("io.github.kavahub.learnjava.StaticVariableTest");

        Class<?>[] nestedClasses = staticVariableDemo.getClasses();

        assertThat(nestedClasses.length).isEqualTo(1);

        Class<?> nestedClass = nestedClasses[0];
        assertThat(nestedClass.getName()).isEqualTo("io.github.kavahub.learnjava.StaticVariableTest$StaticVariableDemo");

        Field field1 = nestedClass.getField("j");
        assertThat(field1.getInt(nestedClass)).isEqualTo(20);
    }

    public static class StaticVariableDemo {
        public static int i;
        public static int j = 20;
        public static int z;

        static {
            z = 30;
            a = 40;
        }

        public static int a = 50;

        public static final int b = 100;

        public StaticVariableDemo() {
        }

        // 位置不一样，结果不同
        // static {
        //     z = 30;
        //     a = 40;
        // }
    }

}
