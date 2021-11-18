package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * LinkedHashMap是一个Map实现，它维护其元素的插入顺序。当我们将true传递给accessOrder时，我们将实例化一个访问顺序LinkedHashMap
 * 。 访问顺序LinkedHashMap维护从最近使用到最近使用的元素的访问顺序，而不是插入顺序。
 */
public class MapFirstPairTest {
    private Map.Entry<Integer, String> getFirstPairUsingIterator(Map<Integer, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }

        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();

        if (iterator.hasNext()) {
            return iterator.next();
        }

        return null;
    }

    private Map.Entry<Integer, String> getFirstPairUsingStream(Map<Integer, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }

        Set<Map.Entry<Integer, String>> entrySet = map.entrySet();

        return entrySet.stream().findFirst().get();
    }

    private Map<Integer, String> populateMapValues(Map<Integer, String> map) {
        if (map != null) {
            map.put(5, "A");
            map.put(1, "B");
            map.put(2, "C");
        }
        return map;
    }

    @Test
    public void whenUsingIteratorForHashMap_thenFirstPairWhichWasNotInsertedFirst() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap = populateMapValues(hashMap);

        Map.Entry<Integer, String> actualValue = getFirstPairUsingIterator(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(1, "B");
        Map.Entry<Integer, String> pairInsertedFirst = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
        assertNotEquals(pairInsertedFirst, actualValue);
    }

    @Test
    public void whenUsingStreamForHashMap_thenFirstPairWhichWasNotInsertedFirst() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap = populateMapValues(hashMap);
        Map.Entry<Integer, String> actualValue = getFirstPairUsingStream(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(1, "B");
        Map.Entry<Integer, String> pairInsertedFirst = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
        assertNotEquals(pairInsertedFirst, actualValue);
    }

    @Test
    public void whenUsingIteratorForLinkedHashMap_thenFirstPairWhichWasInsertedFirst() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap = populateMapValues(linkedHashMap);
        Map.Entry<Integer, String> actualValue = getFirstPairUsingIterator(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenUsingStreamForLinkedHashMap_thenFirstPairWhichWasInsertedFirst() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap = populateMapValues(linkedHashMap);

        Map.Entry<Integer, String> actualValue = getFirstPairUsingStream(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInHashMap_thenFirstPairChangedUsingIterator() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap = populateMapValues(hashMap);

        hashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = getFirstPairUsingIterator(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(0, "D");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInHashMap_thenFirstPairChangedUsingStream() {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap = populateMapValues(hashMap);

        hashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = getFirstPairUsingStream(hashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(0, "D");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInLinkedHashMap_thenFirstPairRemainUnchangedUsingIterator() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap = populateMapValues(linkedHashMap);

        linkedHashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = getFirstPairUsingIterator(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void whenAddedAnElementInLinkedHashMap_thenFirstPairRemainUnchangedUsingStream() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap = populateMapValues(linkedHashMap);

        linkedHashMap.put(0, "D");
        Map.Entry<Integer, String> actualValue = getFirstPairUsingStream(linkedHashMap);
        Map.Entry<Integer, String> expectedValue = new AbstractMap.SimpleEntry<Integer, String>(5, "A");

        assertEquals(expectedValue, actualValue);
    }
}
