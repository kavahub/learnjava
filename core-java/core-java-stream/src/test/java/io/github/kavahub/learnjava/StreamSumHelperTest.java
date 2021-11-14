package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StreamSumHelperTest {
    
    @Test
    public void givenListOfIntegersWhenSummingUsingCustomizedAccumulatorThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = StreamSumHelper.getSumUsingCustomizedAccumulator(integers);
        assertEquals(15, sum.intValue());

    }

    @Test
    public void givenListOfIntegersWhenSummingUsingJavaAccumulatorThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = StreamSumHelper.getSumUsingJavaAccumulator(integers);
        assertEquals(15, sum.intValue());
    }

    @Test
    public void givenListOfIntegersWhenSummingUsingReduceThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = StreamSumHelper.getSumUsingReduce(integers);
        assertEquals(15, sum.intValue());
    }

    @Test
    public void givenListOfIntegersWhenSummingUsingCollectThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = StreamSumHelper.getSumUsingCollect(integers);
        assertEquals(15, sum.intValue());
    }

    @Test
    public void givenListOfIntegersWhenSummingUsingSumThenCorrectValueReturned() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = StreamSumHelper.getSumUsingSum(integers);
        assertEquals(15, sum.intValue());
    }
}
