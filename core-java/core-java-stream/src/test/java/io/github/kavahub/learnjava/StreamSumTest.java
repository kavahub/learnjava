package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.StreamSum.*;

public class StreamSumTest {
    
    @Test
    public void givenListOfIntegersWhenSummingUsingCustomizedAccumulatorThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = getSumUsingCustomizedAccumulator(integers);
        assertEquals(15, sum.intValue());

    }

    @Test
    public void givenListOfIntegersWhenSummingUsingJavaAccumulatorThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = getSumUsingJavaAccumulator(integers);
        assertEquals(15, sum.intValue());
    }

    @Test
    public void givenListOfIntegersWhenSummingUsingReduceThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = getSumUsingReduce(integers);
        assertEquals(15, sum.intValue());
    }

    @Test
    public void givenListOfIntegersWhenSummingUsingCollectThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = getSumUsingCollect(integers);
        assertEquals(15, sum.intValue());
    }

    @Test
    public void givenListOfIntegersWhenSummingUsingSumThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = getSumUsingSum(integers);
        assertEquals(15, sum.intValue());
    }
}
