package io.github.kavahub.learnjava.reflect;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NullPropertyReflectionHelper {
    static List<String> getNullPropertiesList(Object o) throws Exception {
        PropertyDescriptor[] propDescArr = Introspector.getBeanInfo(o.getClass(), Object.class).getPropertyDescriptors();

        return Arrays.stream(propDescArr)
          .filter(nulls(o))
          .map(PropertyDescriptor::getName)
          .collect(Collectors.toList());
    }

    private static Predicate<PropertyDescriptor> nulls(Object o) {
        return pd -> {
            boolean result = false;
            try {
                Method getterMethod = pd.getReadMethod();
                result = (getterMethod != null && getterMethod.invoke(o) == null);
            } catch (Exception e) {
                log.error("error invoking getter method");
            }
            return result;
        };
    }    
}
