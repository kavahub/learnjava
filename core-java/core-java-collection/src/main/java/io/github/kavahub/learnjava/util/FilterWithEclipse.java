package io.github.kavahub.learnjava.util;

import java.util.Collection;

import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.utility.Iterate;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FilterWithEclipse {
    public Collection<Integer> findEvenNumbers(Collection<Integer> baseCollection) {
        Predicate<Integer> eclipsePredicate = item -> item % 2 == 0;
        Collection<Integer> filteredList = Lists.mutable.ofAll(baseCollection)
            .select(eclipsePredicate);

        return filteredList;
    }

    public Collection<Integer> findEvenNumbersUsingIterate(Collection<Integer> baseCollection) {
        Predicate<Integer> eclipsePredicate = new Predicate<Integer>() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(Integer arg0) {
                return arg0 % 2 == 0;
            }
        };
        Collection<Integer> filteredList = Iterate.select(baseCollection, eclipsePredicate);

        return filteredList;
    }
}
