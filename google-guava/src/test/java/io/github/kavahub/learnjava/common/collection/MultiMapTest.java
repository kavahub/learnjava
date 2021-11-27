package io.github.kavahub.learnjava.common.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link MultiMap} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MultiMapTest {
    @Test
    public void givenMap_whenAddTwoValuesForSameKey_shouldOverridePreviousKey() {
        //given
        String key = "a-key";
        Map<String, String> map = new LinkedHashMap<>();

        //when
        map.put(key, "firstValue");
        map.put(key, "secondValue");

        //then
        assertEquals(1, map.size());
    }

    @Test
    public void givenMultiMap_whenAddTwoValuesForSameKey_shouldHaveTwoEntriesInMap() {
        //given
        String key = "a-key";
        Multimap<String, String> map = ArrayListMultimap.create();

        //when
        map.put(key, "firstValue");
        map.put(key, "secondValue");

        //then
        assertEquals(2, map.size());
        assertThat(map.get(key)).isInstanceOf(List.class);
        assertThat(map.get(key), containsInAnyOrder("firstValue", "secondValue"));
      }

    @Test
    public void givenMapOfListValues_whenAddTwoValuesForSameKey_shouldHaveTwoElementsInList() {
        //given
        String key = "a-key";
        Map<String, List<String>> map = new LinkedHashMap<>();

        //when
        List<String> values = map.get(key);
        if(values == null){
            values = new LinkedList<>();
            values.add("firstValue");
            values.add("secondValue");
        }
        map.put(key, values);

        //then
        assertEquals(1, map.size());
    }
}
