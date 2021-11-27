package io.github.kavahub.learnjava.enhance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Function;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link MapFromSet} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MapFromSetTest {
    @Test
    public void givenStringSet_whenMapsToElementLength_thenCorrect() {
        Function<Integer, String> function = new Function<Integer, String>() {
            @Override
            public String apply(Integer from) {
                // 返回int变量的二进制表示的字符串
                return Integer.toBinaryString(from);
            }
        };
        Set<Integer> set = new TreeSet<>(Arrays.asList(32, 64, 128));
        Map<Integer, String> map = new MapFromSet<Integer, String>(set, function);
        assertTrue(map.get(32).equals("100000")
                && map.get(64).equals("1000000")
                && map.get(128).equals("10000000"));
    }

    @Test
    public void givenIntSet_whenMapsToElementBinaryValue_thenCorrect() {
        Function<String, Integer> function = new Function<String, Integer>() {

            @Override
            public Integer apply(String from) {
                return from.length();
            }
        };
        Set<String> set = new TreeSet<>(Arrays.asList(
                "four", "three", "twelve"));
        Map<String, Integer> map = new MapFromSet<String, Integer>(set,
                function);
        assertTrue(map.get("four") == 4 && map.get("three") == 5
                && map.get("twelve") == 6);
    }

    @Test
    public void givenSet_whenNewSetElementAddedAndMappedLive_thenCorrect() {
        Function<String, Integer> function = new Function<String, Integer>() {

            @Override
            public Integer apply(String from) {
                return from.length();
            }
        };
        Set<String> set = new TreeSet<>(Arrays.asList(
                "four", "three", "twelve"));
        Map<String, Integer> map = new MapFromSet<String, Integer>(set,
                function);
        set.add("one");
        assertTrue(map.get("one") == 3 && map.size() == 4);
    }   
}
