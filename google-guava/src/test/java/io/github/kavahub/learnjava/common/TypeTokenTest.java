package io.github.kavahub.learnjava.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.TypeToken;

import org.junit.jupiter.api.Test;

public class TypeTokenTest {
    @Test
    public void whenCheckingIsAssignableFrom_shouldReturnTrueEvenIfGenericIsSpecified() throws Exception {
        ArrayList<String> stringList = new ArrayList<>();
        ArrayList<Integer> intList = new ArrayList<>();
        boolean isAssignableFrom = stringList.getClass().isAssignableFrom(intList.getClass());

        assertTrue(isAssignableFrom);
    }

    @Test
    public void whenCheckingIsSupertypeOf_shouldReturnFalseIfGenericIsSpecified() throws Exception {
        TypeToken<ArrayList<String>> listString = new TypeToken<ArrayList<String>>() {
        };
        TypeToken<ArrayList<Integer>> integerString = new TypeToken<ArrayList<Integer>>() {
        };

        boolean isSupertypeOf = listString.isSupertypeOf(integerString);

        assertFalse(isSupertypeOf);
    }

    @Test
    public void whenCheckingIsSubtypeOf_shouldReturnTrueIfClassIsExtendedFrom() throws Exception {
        TypeToken<ArrayList<String>> stringList = new TypeToken<ArrayList<String>>() {
        };
        TypeToken<List<?>> list = new TypeToken<List<?>>() {
        };

        boolean isSubtypeOf = stringList.isSubtypeOf(list);

        assertTrue(isSubtypeOf);
    }
}
