package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MapComputeIfAbsentTest {
    @Test
    public void whenKeyIsPresent_thenFetchTheValue() {
        Map<String, Integer> stringLength = new HashMap<>();
        stringLength.put("John", 5);
        assertEquals((long) stringLength.computeIfAbsent("John", s -> s.length()), 5);
    }

    @Test
    public void whenKeyIsNotPresent_thenComputeTheValueUsingMappingFunctionAndStore() {
        Map<String, Integer> stringLength = new HashMap<>();
        assertEquals((long) stringLength.computeIfAbsent("John", s -> s.length()), 4);
        assertEquals((long) stringLength.get("John"), 4);
    }

    @Test
    public void whenMappingFunctionReturnsNull_thenDoNotRecordMapping() {
        Map<String, Integer> stringLength = new HashMap<>();
        assertEquals(stringLength.computeIfAbsent("John", s -> null), null);
        assertNull(stringLength.get("John"));
    }

    @Test
    public void whenMappingFunctionThrowsException_thenExceptionIsRethrown() {
        Map<String, Integer> stringLength = new HashMap<>();
        assertThrows(RuntimeException.class, () -> {
            stringLength.computeIfAbsent("John", s -> {
                throw new RuntimeException();
            });
        });

    }
}
