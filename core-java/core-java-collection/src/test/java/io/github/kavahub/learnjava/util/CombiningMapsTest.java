package io.github.kavahub.learnjava.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.CombiningMaps.*;

public class CombiningMapsTest {
    private static final Map<String, String> first = new HashMap<>();
    private static final Map<String, String> second = new HashMap<>();
    private static Map<String, String> expected = new HashMap<>();
    
    static {
    	first.put("one", "first String");
    	first.put("two", "second String");
    	
    	second.put("three", "third String");
    	second.put("four", "fourth String");

    	expected.put("one", "first String");
    	expected.put("two", "second String");
    	expected.put("three", "third String");
    	expected.put("four", "fourth String");
    }
    
    @Test
    public void givenTwoMaps_whenUsingNativeJava_thenMapsCombined() {
    	assertThat(usingPlainJava(first, second), is(expected));
    }

    
    @Test
    public void givenTwoMaps_whenUsingForEach_thenMapsCombined() {
    	assertThat(usingJava8ForEach(first, second), is(expected));
    }
    
    @Test
    public void givenTwoMaps_whenUsingFlatMaps_thenMapsCombined() {
    	assertThat(usingJava8FlatMaps(first, second), is(expected));
    }
    
    @Test
    public void givenTwoMaps_whenUsingApacheCommons_thenMapsCombined() {
    	assertThat(usingApacheCommons(first, second), is(expected));
    }
    
    @Test
    public void givenTwoMaps_whenUsingGuava_thenMapsCombined() {
    	assertThat(usingGuava(first, second), is(expected));
    }

    @Test
    public void giveDuplication_whenCombined_thenGetValue() {
        final String key = "key";
        Map<String, String> one = new HashMap<>(); 
        Map<String, String> two = new HashMap<>(); 
        one.put(key, "A string");
        two.put(key, "Another string");
        
        assertEquals("Another string", CombiningMaps.usingPlainJava(one, two).get(key));

        one.clear();
        two.clear();
        one.put(key, "A string");
        two.put(key, "Another string");
        assertEquals("A string", CombiningMaps.usingJava8ForEach(one, two).get(key));


        one.clear();
        two.clear();
        one.put(key, "A string");
        two.put(key, "Another string");
        assertEquals("A string", CombiningMaps.usingJava8FlatMaps(one, two).get(key));


        assertEquals("Another string", CombiningMaps.usingApacheCommons(one, two).get(key));


        assertThrows(IllegalArgumentException.class, () -> CombiningMaps.usingGuava(one, two).get(key));
    }
}
