package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 
 * Java10功能示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class Java10FeaturesTest {
    private static  List<Integer> someIntList;
    
    @BeforeAll
    public static void setup() {
        someIntList = new ArrayList<>();
        
        someIntList.add(1);
        someIntList.add(2);
        someIntList.add(3);
    }

    @Test
    public void whenVarInitWithString_thenGetStringTypeVar() {
        var message = "Hello, Java 10";
        assertTrue(message instanceof String);
    }

    @Test
    public void whenVarInitWithAnonymous_thenGetAnonymousType() {
        var obj = new Object() {};
        assertFalse(obj.getClass().equals(Object.class));
    }

    @Test
    public void whenModifyCopyOfList_thenThrowsException() {
        List<Integer> copyList = List.copyOf(someIntList);
        assertThrows(UnsupportedOperationException.class, () -> copyList.add(4));
    }

    @Test
    public void whenModifyToUnmodifiableList_thenThrowsException() {
        List<Integer> evenList = someIntList.stream()
          .filter(i -> i % 2 == 0)
          .collect(Collectors.toUnmodifiableList());
        assertThrows(UnsupportedOperationException.class, () -> evenList.add(4));  
    }

    @Test
    public void whenListContainsInteger_OrElseThrowReturnsInteger() {
        Integer firstEven = someIntList.stream()
          .filter(i -> i % 2 == 0)
          .findFirst()
          .orElseThrow();
        Assertions.assertThat(firstEven).isEqualTo(Integer.valueOf(2));
    }   
}
