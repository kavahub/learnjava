package io.github.kavahub.learnjava;


import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * 
 * Jav9 {@link Objects} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class Java9ObjectsAPITest {
    private List<String> aMethodReturningNullList(){
        return null;
    }

    @Test
    public void givenNullObject_whenRequireNonNullElse_thenElse(){
        List<?> aList = Objects.<List<?>>requireNonNullElse(
                aMethodReturningNullList(), Collections.EMPTY_LIST);
        assertThat(aList).isEqualTo(Collections.EMPTY_LIST);
    }

    private List<String> aMethodReturningNonNullList(){
        return List.of("item1", "item2");
    }

    @SuppressWarnings("all")
    @Test
    public void givenObject_whenRequireNonNullElse_thenObject(){
        List<String> aList = Objects.<List>requireNonNullElse(
                aMethodReturningNonNullList(), Collections.EMPTY_LIST);
        assertThat(aList).isEqualTo(List.of("item1", "item2"));
    }

    @Test
    public void givenNull_whenRequireNonNullElse_thenException(){
        assertThrows(NullPointerException.class, () -> Objects.requireNonNullElse(null, null));
    }

    @SuppressWarnings("all")
    @Test
    public void givenObject_whenRequireNonNullElseGet_thenObject(){
        List<String> aList = Objects.<List>requireNonNullElseGet(null, List::of);
        assertThat(aList).isEqualTo(List.of());
    }

    @Test
    public void givenNumber_whenInvokeCheckIndex_thenNumber(){
        int length = 5;
        assertThat(Objects.checkIndex(4, length)).isEqualTo(4);
    }

    @Test
    public void givenOutOfRangeNumber_whenInvokeCheckIndex_thenException(){
        int length = 5;

        assertThrows(IndexOutOfBoundsException.class, () -> Objects.checkIndex(5, length));
    }


    @Test
    public void givenSubRange_whenCheckFromToIndex_thenNumber(){
        int length = 6;
        assertThat(Objects.checkFromToIndex(2,length,length)).isEqualTo(2);
    }

    @Test
    public void givenInvalidSubRange_whenCheckFromToIndex_thenException(){
        int length = 6;

        assertThrows(IndexOutOfBoundsException.class, () -> Objects.checkFromToIndex(2,7,length));
    }


    @Test
    public void givenSubRange_whenCheckFromIndexSize_thenNumber(){
        int length = 6;
        assertThat(Objects.checkFromIndexSize(2,3,length)).isEqualTo(2);
    
    }

    @Test
    public void givenInvalidSubRange_whenCheckFromIndexSize_thenException(){
        int length = 6;

        assertThrows(IndexOutOfBoundsException.class, () -> Objects.checkFromIndexSize(2, 6, length));
    }    
}
