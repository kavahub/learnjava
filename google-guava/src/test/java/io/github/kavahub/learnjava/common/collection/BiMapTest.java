package io.github.kavahub.learnjava.common.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;

import org.junit.jupiter.api.Test;

/**
 * BiMap提供了一种新的集合类型，它提供了key和value的双向关联的数据结构
 */
public class BiMapTest {
    @Test
    public void whenQueryByValue_returnsKey() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();
        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");

        final String countryCapitalName = capitalCountryBiMap.inverse().get("India");

        assertEquals("New Delhi", countryCapitalName);
    }

    @Test
    public void whenCreateBiMapFromExistingMap_returnsKey() {
        final Map<String, String> capitalCountryMap = new HashMap<>();
        capitalCountryMap.put("New Delhi", "India");
        capitalCountryMap.put("Washingon, D.C.", "USA");
        capitalCountryMap.put("Moscow", "Russia");
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create(capitalCountryMap);

        final String countryCapitalName = capitalCountryBiMap.inverse().get("India");

        assertEquals("New Delhi", countryCapitalName);
    }

    @Test
    public void whenQueryByKey_returnsValue() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");

        assertEquals("USA", capitalCountryBiMap.get("Washingon, D.C."));
    }

    @Test
    public void whenSameValueIsPresent_throwsException() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");
        assertThrows(IllegalArgumentException.class, () -> capitalCountryBiMap.put("Trump", "USA"));
    }

    @Test
    public void givenSameValueIsPresent_whenForcePut_completesSuccessfully() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");
        capitalCountryBiMap.forcePut("Trump", "USA");

        assertEquals("USA", capitalCountryBiMap.get("Trump"));
        assertEquals("Trump", capitalCountryBiMap.inverse().get("USA"));
    }

    @Test
    public void whenSameKeyIsPresent_replacesAlreadyPresent() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");
        capitalCountryBiMap.put("Washingon, D.C.", "HongKong");

        assertEquals("HongKong", capitalCountryBiMap.get("Washingon, D.C."));
    }

    @Test
    public void whenUsingImmutableBiMap_allowsPutSuccessfully() {
        final BiMap<String, String> capitalCountryBiMap = new ImmutableBiMap.Builder<String, String>()
                .put("New Delhi", "India").put("Washingon, D.C.", "USA").put("Moscow", "Russia").build();

        assertEquals("USA", capitalCountryBiMap.get("Washingon, D.C."));
    }

    @Test
    public void whenUsingImmutableBiMap_doesntAllowRemove() {
        final BiMap<String, String> capitalCountryBiMap = new ImmutableBiMap.Builder<String, String>()
                .put("New Delhi", "India").put("Washingon, D.C.", "USA").put("Moscow", "Russia").build();

        assertThrows(UnsupportedOperationException.class, () -> capitalCountryBiMap.remove("New Delhi"));
    }

    @Test
    public void whenUsingImmutableBiMap_doesntAllowPut() {
        final BiMap<String, String> capitalCountryBiMap = new ImmutableBiMap.Builder<String, String>()
                .put("New Delhi", "India").put("Washingon, D.C.", "USA").put("Moscow", "Russia").build();

        assertThrows(UnsupportedOperationException.class, () -> capitalCountryBiMap.put("New York", "USA"));
    }

    private enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    @Test
    public void whenUsingEnumAsKeyInMap_replacesAlreadyPresent() {
        final BiMap<Operation, String> operationStringBiMap = EnumHashBiMap.create(Operation.class);

        operationStringBiMap.put(Operation.ADD, "Add");
        operationStringBiMap.put(Operation.SUBTRACT, "Subtract");
        operationStringBiMap.put(Operation.MULTIPLY, "Multiply");
        operationStringBiMap.put(Operation.DIVIDE, "Divide");

        assertEquals("Divide", operationStringBiMap.get(Operation.DIVIDE));
    }
}
