package io.github.kavahub.learnjava.combine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.exec.util.MapUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CombiningMaps {
    public <K, V> Map<K, V> usingPlainJava(Map<K, V> first, Map<K, V> second) {
    	Map<K, V> combined = new HashMap<>();
    	combined.putAll(first);
    	combined.putAll(second);
        return combined;
    }
    
    /**
     * 注意 first集合被修改
     * 
     * @param first
     * @param second
     * @return
     */
    public <K, V> Map<K, V> usingJava8ForEach(Map<K, V> first, Map<K, V> second) {
    	second.forEach((key, value) -> first.merge(key, value, (old, cur) -> old));
        return first;
    }

    public <K, V> Map<K, V> usingJava8FlatMaps(Map<K, V> first, Map<K, V> second) {
		Map<K, V> combined = Stream.of(first, second).map(Map::entrySet).flatMap(Collection::stream)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (old, cur) -> old));
		return combined;

    }
    
    public <K, V> Map<K, V> usingApacheCommons(Map<K, V> first, Map<K, V> second) {
        Map<K, V> combined = MapUtils.merge(first, second);
        return combined;
    }

    public <K, V> Map<K, V> usingGuava(Map<K, V> first, Map<K, V> second) {
        Map<K, V> combined = ImmutableMap.<K, V>builder()
          .putAll(first)
          .putAll(second)
          .build();
        return combined;
    }
}
