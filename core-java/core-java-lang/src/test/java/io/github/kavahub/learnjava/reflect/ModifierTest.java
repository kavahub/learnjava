package io.github.kavahub.learnjava.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@link Modifier}示例
 * 
 */
public class ModifierTest {
    @Test
    void givenAbstractClass_whenCheckModifierIsAbstract_thenTrue() {
        Class<AbstractExample> clazz = AbstractExample.class;
        Assertions.assertTrue(Modifier.isAbstract(clazz.getModifiers()));
    }

    @Test
    void givenInterface_whenCheckModifierIsAbstract_thenTrue() {
        Class<InterfaceExample> clazz = InterfaceExample.class;
        Assertions.assertTrue(Modifier.isAbstract(clazz.getModifiers()));
    }

    @Test
    void givenAbstractClass_whenCheckIsAbstractClass_thenTrue() {
        Class<AbstractExample> clazz = AbstractExample.class;
        int mod = clazz.getModifiers();
        Assertions.assertTrue(Modifier.isAbstract(mod) && !Modifier.isInterface(mod));
    }

    @Test
    void givenConcreteClass_whenCheckIsAbstractClass_thenFalse() {
        Class<Date> clazz = Date.class;
        int mod = clazz.getModifiers();
        Assertions.assertFalse(Modifier.isAbstract(mod) && !Modifier.isInterface(mod));
    } 
    
    @Test
    void whenCheckStaticMethod_ThenSuccess() throws Exception {
        Method method = AbstractExample.class.getMethod("getAuthorName", new Class<?>[0]);
        Assertions.assertTrue(Modifier.isStatic(method.getModifiers()));
    }

    @Test
    void whenCheckAllStaticMethods_thenSuccess() {
        List<Method> methodList = Arrays.asList(AbstractExample.class.getMethods())
          .stream()
          .filter(method -> Modifier.isStatic(method.getModifiers()))
          .collect(Collectors.toList());
        Assertions.assertEquals(1, methodList.size());
    }

    public static abstract class AbstractExample {

        public static String getAuthorName() {
            return "Umang Budhwar";
        }
    
        public abstract LocalDate getLocalDate();
    
        public abstract LocalTime getLocalTime();
    }

    public static interface InterfaceExample {
    }
    
    
}
