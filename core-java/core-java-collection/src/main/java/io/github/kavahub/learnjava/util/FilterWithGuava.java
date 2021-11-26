package io.github.kavahub.learnjava.util;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FilterWithGuava {
    public Collection<Integer> findEvenNumbers(Collection<Integer> baseCollection) {
        Predicate<Integer> guavaPredicate = item -> item % 2 == 0;

        Collection<Integer> filteredCollection = Collections2.filter(baseCollection, guavaPredicate);
        return filteredCollection;
    }    
}
