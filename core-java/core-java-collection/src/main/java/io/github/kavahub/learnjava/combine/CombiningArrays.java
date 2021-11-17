package io.github.kavahub.learnjava.combine;

import java.util.Arrays;
import java.util.stream.Stream;

import com.google.common.collect.ObjectArrays;

import org.apache.commons.lang3.ArrayUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CombiningArrays {
    public Object[] usingNativeJava(Object[] first, Object[] second) {
        Object[] combined = new Object[first.length + second.length];
        System.arraycopy(first, 0, combined, 0, first.length);
        System.arraycopy(second, 0, combined, first.length, second.length);
        return combined;
    }
    
    public Object[] usingJava8ObjectStream(Object[] first, Object[] second) {
        Object[] combined = Stream.concat(Arrays.stream(first), Arrays.stream(second)).toArray();
        return combined;
    }

    public Object[] usingJava8FlatMaps(Object[] first, Object[] second) {
        Object[] combined = Stream.of(first, second).flatMap(Stream::of).toArray(String[]::new);
        return combined;
    }
    
    public Object[] usingApacheCommons(Object[] first, Object[] second) {
        Object[] combined = ArrayUtils.addAll(first, second);
        return combined;
    }

    public Object[] usingGuava(Object[] first, Object[] second) {
        Object [] combined = ObjectArrays.concat(first, second, Object.class);
        return combined;
    }
}
