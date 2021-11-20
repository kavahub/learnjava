package io.github.kavahub.learnjava.reflect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 无返回值方法列表
 * 
 */
public class ListVoidMethodTest {
    @Test
    void givenClass_whenGettingVoidMethodsByReflection() {
        Method[] calculatorMethods = Simple.class.getDeclaredMethods();
        List<Method> calculatorVoidMethods = Arrays.stream(calculatorMethods)
          .filter(method -> method.getReturnType().equals(Void.TYPE))
          .collect(Collectors.toList());

        assertThat(calculatorVoidMethods)
          .allMatch(method -> Arrays.asList("clear", "print").contains(method.getName()));
    }

    public static class Simple {
        private int result = 0;
    
        public int add(int number) {
            return result += number;
        }
    
        public int sub(int number) {
            return result -= number;
        }
    
        public Void get() {
            return null;
        }

        public void clear() {
            result = 0;
        }
    
        public void print() {
            System.out.println(result);
        }
    }
}
