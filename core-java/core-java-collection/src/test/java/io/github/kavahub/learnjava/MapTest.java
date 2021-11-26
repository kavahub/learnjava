package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.enhance.LRUCache;

public class MapTest {
    @Test
    public void givenHashMap_whenRetrievesKeyset_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "bSystem.out.println");

        Set<String> keys = map.keySet();

        assertEquals(2, keys.size());
        assertTrue(keys.contains("name"));
        assertTrue(keys.contains("type"));
    }

    @Test
    public void givenHashMap_whenRetrievesValues_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "bSystem.out.println");

        Collection<String> values = map.values();

        assertEquals(2, values.size());
        assertTrue(values.contains("baeldung"));
        assertTrue(values.contains("bSystem.out.println"));
    }

    @Test
    public void givenHashMap_whenRetrievesEntries_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "bSystem.out.println");

        Set<Entry<String, String>> entries = map.entrySet();

        assertEquals(2, entries.size());
        for (Entry<String, String> e : entries) {
            String key = e.getKey();
            String val = e.getValue();
            assertTrue(key.equals("name") || key.equals("type"));
            assertTrue(val.equals("baeldung") || val.equals("bSystem.out.println"));
        }
    }

    @Test
    public void givenKeySet_whenChangeReflectsInMap_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "bSystem.out.println");

        assertEquals(2, map.size());

        Set<String> keys = map.keySet();

        keys.remove("name");
        assertEquals(1, map.size());
    }

    @Test
    public void givenIterator_whenFailsFastOnModification_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "bSystem.out.println");

        Set<String> keys = map.keySet();
        Iterator<String> it = keys.iterator();
        map.remove("type");

        assertThrows(ConcurrentModificationException.class, () -> {
            while (it.hasNext()) {
                it.next();
            }
        });

    }

    public void givenIterator_whenRemoveWorks_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "bSystem.out.println");

        Set<String> keys = map.keySet();
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        assertEquals(0, map.size());
    }

    @Test
    public void whenHashCodeIsCalledOnPut_thenCorrect() {
        MyKey key = new MyKey(1, "name");
        Map<MyKey, String> map = new HashMap<>();
        map.put(key, "val");
    }

    @Test
    public void whenHashCodeIsCalledOnGet_thenCorrect() {
        MyKey key = new MyKey(1, "name");
        Map<MyKey, String> map = new HashMap<>();
        map.put(key, "val");
        map.get(key);
    }

    @Test
    public void whenGetWorks_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "val");

        String val = map.get("key");

        assertEquals("val", val);
    }

    @Test
    public void givenNewKey_whenPutReturnsNull_thenCorrect() {
        Map<String, String> map = new HashMap<>();

        String rtnVal = map.put("key1", "val1");

        assertNull(rtnVal);
    }

    @Test
    public void givenUnmappedKey_whenGetReturnsNull_thenCorrect() {
        Map<String, String> map = new HashMap<>();

        String rtnVal = map.get("key1");

        assertNull(rtnVal);
    }

    @Test
    public void givenNullVal_whenPutReturnsNull_thenCorrect() {
        Map<String, String> map = new HashMap<>();

        String rtnVal = map.put("key1", null);

        assertNull(rtnVal);
    }

    @Test
    public void givenNullKeyAndVal_whenAccepts_thenCorrect() {
        Map<String, String> map = new HashMap<>();

        map.put(null, null);
    }

    @Test
    public void givenNullVal_whenRetrieves_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("key", null);

        String val = map.get("key");

        assertNull(val);
    }

    @Test
    public void whenContainsDistinguishesNullValues_thenCorrect() {
        Map<String, String> map = new HashMap<>();

        String val1 = map.get("key");
        boolean valPresent = map.containsKey("key");

        assertNull(val1);
        assertFalse(valPresent);

        map.put("key", null);
        String val = map.get("key");
        valPresent = map.containsKey("key");

        assertNull(val);
        assertTrue(valPresent);

    }

    @Test
    public void whenPutReturnsPrevValue_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "val1");
        String rtnVal = map.put("key1", "val2");

        assertEquals("val1", rtnVal);
    }

    @Test
    public void whenCallsEqualsOnCollision_thenCorrect() {
        HashMap<MyKey, String> map = new HashMap<>();
        MyKey k1 = new MyKey(1, "firstKey");
        MyKey k2 = new MyKey(2, "secondKey");
        MyKey k3 = new MyKey(2, "thirdKey");

        System.out.println("storing value for k1");
        map.put(k1, "firstValue");

        System.out.println("storing value for k2");
        map.put(k2, "secondValue");

        System.out.println("storing value for k3");
        map.put(k3, "thirdValue");

        System.out.println("retrieving value for k1");
        String v1 = map.get(k1);

        System.out.println("retrieving value for k2");
        String v2 = map.get(k2);

        System.out.println("retrieving value for k3");
        String v3 = map.get(k3);

        assertEquals("firstValue", v1);
        assertEquals("secondValue", v2);
        assertEquals("thirdValue", v3);

    }

    @Test
    public void givenLinkedHashMap_whenGetsOrderedKeyset_thenCorrect() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, null);
        map.put(2, null);
        map.put(3, null);
        map.put(4, null);
        map.put(5, null);
        Set<Integer> keys = map.keySet();
        Integer[] arr = keys.toArray(new Integer[0]);
        for (int i = 0; i < arr.length; i++) {
            assertEquals(Integer.valueOf(i + 1), arr[i]);

        }
    }

    @Test
    public void givenLinkedHashMap_whenAccessOrderWorks_thenCorrect() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>(16, .75f, true);
        map.put(1, null);
        map.put(2, null);
        map.put(3, null);
        map.put(4, null);
        map.put(5, null);
        Set<Integer> keys = map.keySet();
        assertEquals("[1, 2, 3, 4, 5]", keys.toString());
        map.get(4);
        assertEquals("[1, 2, 3, 5, 4]", keys.toString());
        map.get(1);
        assertEquals("[2, 3, 5, 4, 1]", keys.toString());
        map.get(3);
        assertEquals("[2, 5, 4, 1, 3]", keys.toString());
    }

    @Test
    public void givenLinkedHashMap_whenRemovesEldestEntry_thenCorrect() {
        LRUCache<Integer, String> map = new LRUCache<>(5);
        map.put(1, null);
        map.put(2, null);
        map.put(3, null);
        map.put(4, null);
        map.put(5, null);
        Set<Integer> keys = map.keySet();
        assertEquals("[1, 2, 3, 4, 5]", keys.toString());
        map.put(6, null);
        assertEquals("[2, 3, 4, 5, 6]", keys.toString());
        map.put(7, null);
        assertEquals("[3, 4, 5, 6, 7]", keys.toString());
        map.put(8, null);
        assertEquals("[4, 5, 6, 7, 8]", keys.toString());
    }

    @Test
    public void givenTreeMap_whenOrdersEntriesNaturally_thenCorrect() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(3, "val");
        map.put(2, "val");
        map.put(1, "val");
        map.put(5, "val");
        map.put(4, "val");
        assertEquals("[1, 2, 3, 4, 5]", map.keySet().toString());
    }

    @Test
    public void givenTreeMap_whenOrdersEntriesNaturally_thenCorrect2() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("c", "val");
        map.put("b", "val");
        map.put("a", "val");
        map.put("e", "val");
        map.put("d", "val");

        assertEquals("[a, b, c, d, e]", map.keySet().toString());
    }

    @Test
    public void givenTreeMap_whenOrdersEntriesByComparator_thenCorrect() {
        TreeMap<Integer, String> map = new TreeMap<>(Comparator.reverseOrder());
        map.put(3, "val");
        map.put(2, "val");
        map.put(1, "val");
        map.put(5, "val");
        map.put(4, "val");

        assertEquals("[5, 4, 3, 2, 1]", map.keySet().toString());
    }

    @Test
    public void givenTreeMap_whenPerformsQueries_thenCorrect() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(3, "val");
        map.put(2, "val");
        map.put(1, "val");
        map.put(5, "val");
        map.put(4, "val");

        Integer highestKey = map.lastKey();
        Integer lowestKey = map.firstKey();
        Set<Integer> keysLessThan3 = map.headMap(3).keySet();
        Set<Integer> keysGreaterThanEqTo3 = map.tailMap(3).keySet();

        assertEquals(Integer.valueOf(5), highestKey);
        assertEquals(Integer.valueOf(1), lowestKey);
        assertEquals("[1, 2]", keysLessThan3.toString());
        assertEquals("[3, 4, 5]", keysGreaterThanEqTo3.toString());
    }
}
