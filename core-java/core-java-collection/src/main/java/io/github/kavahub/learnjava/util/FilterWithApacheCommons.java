package io.github.kavahub.learnjava.util;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import lombok.experimental.UtilityClass;

/**
 * 
 * 过滤集合，使用 <code>Apache Commons</code>
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class FilterWithApacheCommons {
    public Collection<Integer> findEvenNumbers(Collection<Integer> baseCollection) {
        Predicate<Integer> apacheEventNumberPredicate = item -> item % 2 == 0;

        CollectionUtils.filter(baseCollection, apacheEventNumberPredicate);
        return baseCollection;
    }
}
