package io.github.kavahub.learnjava.util;

import java.util.Collection;
import java.util.stream.Collectors;

import com.google.common.base.Predicate;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FilterWithStream {
    public <T> Collection<T> filterCollectionHelperMethod(Collection<T> baseCollection, Predicate<T> predicate) {
        return baseCollection.stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }

    public Collection<Integer> findEvenNumbersUsingHelperMethod(Collection<Integer> baseCollection) {
        return filterCollectionHelperMethod(baseCollection, item -> item % 2 == 0);
    }

    public Collection<Integer> findEvenNumbers(Collection<Integer> baseCollection) {
        Predicate<Integer> streamsPredicate = item -> item % 2 == 0;

        return baseCollection.stream()
            .filter(streamsPredicate)
            .collect(Collectors.toList());
    }
}
