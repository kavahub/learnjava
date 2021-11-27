package io.github.kavahub.learnjava.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;

import lombok.experimental.UtilityClass;

/**
 * 
 * 复制 <code>HashMap</code> 集合
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class HashMapCopier {
    public <K, V> HashMap<K, V> copyUsingConstructor(HashMap<K, V> originalMap) {
        return new HashMap<K, V>(originalMap);
    }
    
    @SuppressWarnings("unchecked")
    public <K, V> HashMap<K, V> shallowCopy(HashMap<K, V> originalMap) {
        return (HashMap<K, V>)originalMap.clone();
    }

    public <K, V> HashMap<K, V> copyUsingPut(HashMap<K, V> originalMap) {
        HashMap<K, V> shallowCopy = new HashMap<K, V>();
        Set<Entry<K, V>> entries = originalMap.entrySet();
        for(Map.Entry<K, V> mapEntry: entries) {
            shallowCopy.put(mapEntry.getKey(), mapEntry.getValue());
        }
        
        return shallowCopy;
    }
    
    public <K, V> HashMap<K, V> copyUsingPutAll(HashMap<K, V> originalMap) {
        HashMap<K, V> shallowCopy = new HashMap<K, V>();
        shallowCopy.putAll(originalMap);
        
        return shallowCopy;
    }
    
    public <K, V> HashMap<K, V> copyUsingJava8Stream(HashMap<K, V> originalMap) {
        Set<Entry<K, V>> entries = originalMap.entrySet();
        HashMap<K, V> shallowCopy = (HashMap<K, V>) entries
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        return shallowCopy;
    }

    public <K, V> HashMap<K, V> deepCopy(HashMap<K, V> originalMap) {
        return SerializationUtils.clone(originalMap);
    }
}
