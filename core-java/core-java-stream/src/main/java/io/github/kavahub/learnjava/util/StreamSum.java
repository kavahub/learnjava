package io.github.kavahub.learnjava.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

/**
 * 流合计
 */
@UtilityClass
public class StreamSum {

    private int add(int a, int b) {
        return a + b;
    }

    public Integer getSumUsingCustomizedAccumulator(List<Integer> integers) {
        return integers.stream()
            .reduce(0, StreamSum::add);

    }

    public Integer getSumUsingJavaAccumulator(List<Integer> integers) {
        return integers.stream()
            .reduce(0, Integer::sum);

    }

    public Integer getSumUsingReduce(List<Integer> integers) {
        return integers.stream()
            .reduce(0, (a, b) -> a + b);

    }

    public Integer getSumUsingCollect(List<Integer> integers) {

        return integers.stream()
            .collect(Collectors.summingInt(Integer::intValue));

    }

    public Integer getSumUsingSum(List<Integer> integers) {

        return integers.stream()
            .mapToInt(Integer::intValue)
            .sum();
    }

    public Integer getSumOfMapValues(Map<Object, Integer> map) {
        return map.values()
            .stream()
            .mapToInt(Integer::valueOf)
            .sum();
    }

    public Integer getSumIntegersFromString(String str) {
        Integer sum = Arrays.stream(str.split(" "))
            .filter((s) -> s.matches("\\d+"))
            .mapToInt(Integer::valueOf)
            .sum();

        return sum;
    }
}
