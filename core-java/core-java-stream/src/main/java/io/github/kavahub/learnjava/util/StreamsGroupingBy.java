package io.github.kavahub.learnjava.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lombok.experimental.UtilityClass;

import static java.util.stream.Collectors.*;

/**
 * 流分组
 */
@UtilityClass
public class StreamsGroupingBy {
    public Map<Integer, List<Integer>> findEvenNumbersAfterGroupingByQuantityOfDigits(Collection<Integer> baseCollection) {
        Function<Integer, Integer> getQuantityOfDigits = item -> (int) Math.log10(item) + 1;

        return baseCollection.stream()
            .collect(groupingBy(getQuantityOfDigits, filtering(item -> item % 2 == 0, toList())));
    }

    public Map<Integer, List<Integer>> findEvenNumbersBeforeGroupingByQuantityOfDigits(Collection<Integer> baseCollection) {

        return baseCollection.stream()
            .filter(item -> item % 2 == 0)
            .collect(groupingBy(item -> (int) Math.log10(item) + 1, toList()));
    }    
}
