package io.github.kavahub.learnjava.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Sets;

import org.apache.commons.collections4.SetUtils;

import lombok.experimental.UtilityClass;

/**
 * 
 * 合并 <code>Set</code> 工具
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class CombiningSets {
    public <T> Set<T> usingNativeJava(Set<T> first, Set<T> second) {
        Set<T> combined = new HashSet<>();
        combined.addAll(first);
        combined.addAll(second);
        return combined;
    }
    
    public <T> Set<T> usingJava8ObjectStream(Set<T> first, Set<T> second) {
        Set<T> combined = Stream.concat(first.stream(), second.stream()).collect(Collectors.toSet());
        return combined;
    }

    public <T> Set<T> usingJava8FlatMaps(Set<T> first, Set<T> second) {
        Set<T> combined = Stream.of(first, second).flatMap(Collection::stream).collect(Collectors.toSet());
        return combined;
    }
    
    public <T> Set<T> usingApacheCommons(Set<T> first, Set<T> second) {
        Set<T> combined = SetUtils.union(first, second);
        return combined;
    }

    public <T> Set<T> usingGuava(Set<T> first, Set<T> second) {
        Set<T> combined = Sets.union(first, second);
        return combined;
    }
}
