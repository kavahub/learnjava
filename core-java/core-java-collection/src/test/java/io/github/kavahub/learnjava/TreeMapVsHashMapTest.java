package io.github.kavahub.learnjava;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link TreeMap}, {@link HashMap} 应用比较
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class TreeMapVsHashMapTest {
    @Test
    public void whenInsertObjectsTreeMap_thenNaturalOrder() {
        Map<Integer, String> treemap = new TreeMap<>();
        treemap.put(3, "TreeMap");
        treemap.put(2, "vs");
        treemap.put(1, "HashMap");
        assertThat(treemap.keySet(), Matchers.contains(1, 2, 3));
    }

    @Test
    public void whenInsertNullInTreeMap_thenException() {
        Map<Integer, String> treemap = new TreeMap<>();
        assertThrows(NullPointerException.class, () -> treemap.put(null, "NullPointerException"));
    }

    @Test
    public void whenInsertObjectsHashMap_thenRandomOrder() {
        Map<Integer, String> hashmap = new HashMap<>();
        hashmap.put(3, "TreeMap");
        hashmap.put(2, "vs");
        hashmap.put(1, "HashMap");
        assertThat(hashmap.keySet(), Matchers.containsInAnyOrder(1, 2, 3));
    }

    @Test
    public void whenInsertNullInHashMap_thenInsertsNull() {
        Map<Integer, String> hashmap = new HashMap<>();
        hashmap.put(null, null);
        assertNull(hashmap.get(null));
    }

    @Test
    public void givenHashMapAndTreeMap_whenputDuplicates_thenOnlyUnique() {
        Map<Integer, String> treeMap = new HashMap<>();
        treeMap.put(1, "Baeldung");
        treeMap.put(1, "Baeldung");

        assertTrue(treeMap.size() == 1);

        Map<Integer, String> treeMap2 = new TreeMap<>();
        treeMap2.put(1, "Baeldung");
        treeMap2.put(1, "Baeldung");

        assertTrue(treeMap2.size() == 1);
    }   
}
