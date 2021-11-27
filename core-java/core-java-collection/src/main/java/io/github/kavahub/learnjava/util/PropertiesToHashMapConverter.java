package io.github.kavahub.learnjava.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

import lombok.experimental.UtilityClass;

/**
 * 
 * {@code Properties} 与 {@code HashMap} 相互转换
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class PropertiesToHashMapConverter {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public HashMap<String, String> typeCastConvert(Properties prop) {
        Map step1 = prop;
        Map<String, String> step2 = (Map<String, String>) step1;
        return new HashMap<>(step2);
    }

    public HashMap<String, String> loopConvert(Properties prop) {
        HashMap<String, String> retMap = new HashMap<>();
        for (Map.Entry<Object, Object> entry : prop.entrySet()) {
            retMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
        return retMap;
    }

    public HashMap<String, String> streamConvert(Properties prop) {
        return prop.entrySet().stream().collect(
                Collectors.toMap(
                  e -> String.valueOf(e.getKey()),
                  e -> String.valueOf(e.getValue()),
                  (prev, next) -> next, HashMap::new
                ));
    }

    public HashMap<String, String> guavaConvert(Properties prop) {
        return Maps.newHashMap(Maps.fromProperties(prop));
    }
}
