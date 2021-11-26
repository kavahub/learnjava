package io.github.kavahub.learnjava.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.commons.collections4.ListUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CombiningLists {
    public <T> List<T> usingNativeJava(List<T> first, List<T> second) {
        List<T> combined = new ArrayList<>();
        combined.addAll(first);
        combined.addAll(second);
        return combined;
    }
    
    public <T> List<T> usingJava8ObjectStream(List<T> first, List<T> second) {
        List<T> combined = Stream.concat(first.stream(), second.stream()).collect(Collectors.toList());
        return combined;
    }

    public <T> List<T> usingJava8FlatMaps(List<T> first, List<T> second) {
        List<T> combined = Stream.of(first, second).flatMap(Collection::stream).collect(Collectors.toList());
        return combined;
    }
    
    public <T> List<T> usingApacheCommons(List<T> first, List<T> second) {
        List<T> combined = ListUtils.union(first, second);
        return combined;
    }

    public <T> List<T> usingGuava(List<T> first, List<T> second) {
        Iterable<T> combinedIterables = Iterables.unmodifiableIterable(
        Iterables.concat(first, second));
        
        List<T> combined = Lists.newArrayList(combinedIterables);
        return combined;
    }
    
}
