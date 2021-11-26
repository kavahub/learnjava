package io.github.kavahub.learnjava;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.MapAndStringConverter.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapAndStringConverterTest {
    @Nested
    public class MapToString {

        private Map<Integer, String> wordsByKey = new HashMap<>();
    
        @BeforeEach
        public void setup() {
            wordsByKey.clear();
            wordsByKey.put(1, "one");
            wordsByKey.put(2, "two");
            wordsByKey.put(3, "three");
            wordsByKey.put(4, "four");
        }
    
        @Test
        public void givenMap_WhenUsingIteration_ThenResultingMapIsCorrect() {
            String mapAsString = convertWithIteration(wordsByKey);
            assertEquals("{1=one, 2=two, 3=three, 4=four}", mapAsString);
        }
    
        @Test
        public void givenMap_WhenUsingStream_ThenResultingMapIsCorrect() {
            String mapAsString = convertWithStream(wordsByKey);
            assertEquals("{1=one, 2=two, 3=three, 4=four}", mapAsString);
        }
    
        @Test
        public void givenMap_WhenUsingGuava_ThenResultingMapIsCorrect() {
            String mapAsString = convertWithGuava(wordsByKey);
            assertEquals("1=one,2=two,3=three,4=four", mapAsString);
        }
    
        @Test
        public void givenMap_WhenUsingApache_ThenResultingMapIsCorrect() {
            String mapAsString = convertWithApache(wordsByKey);
            assertEquals("{1=one, 2=two, 3=three, 4=four}", mapAsString);
            MapUtils.debugPrint(System.out, "Map as String", wordsByKey);
        }
    }

    @Nested
    public class StringToMap {

        @Test
        public void givenString_WhenUsingStream_ThenResultingStringIsCorrect() {
            Map<String, String> wordsByKey = convertWithStream("1=one,2=two,3=three,4=four");
            assertEquals(4, wordsByKey.size());
            assertEquals("one", wordsByKey.get("1"));
        }
    
        @Test
        void givenString_WhenUsingGuava_ThenResultingStringIsCorrect() {
            Map<String, String> wordsByKey = convertWithGuava("1=one,2=two,3=three,4=four");
            assertEquals(4, wordsByKey.size());
            assertEquals("one", wordsByKey.get("1"));
        }
    }
}
