package io.github.kavahub.learnjava.filter;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionUtilsCollectionFilter {
    public Collection<Integer> findEvenNumbers(Collection<Integer> baseCollection) {
        Predicate<Integer> apacheEventNumberPredicate = item -> item % 2 == 0;

        CollectionUtils.filter(baseCollection, apacheEventNumberPredicate);
        return baseCollection;
    }
}
