package io.github.kavahub.learnjava.boxing;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

/**
 * 双精度包装类型定位
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
public class DoubleWrapperLookup extends Lookup {
    private Double[] elements;
    private final double pivot = 2d;

    @Override
    @Setup
    public void prepare() {
        double common = 1;
        elements = new Double[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = common;
        }
        elements[s - 1] = pivot;
    }

    @Override
    @TearDown
    public void clean() {
        elements = null;
    }

    @Override
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int findPosition() {
        int index = 0;
        Double pivotWrapper = pivot;
        while (!pivotWrapper.equals(elements[index])) {
            index++;
        }
        return index;

    }

    @Override
    public String getSimpleClassName() {
        return DoubleWrapperLookup.class.getSimpleName();
    }

}
