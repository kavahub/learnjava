package io.github.kavahub.learnjava.boxing;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

/**
 * 长整型基础类型定位
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
public class LongPrimitiveLookup extends Lookup {
    private long[] elements;
    private final long pivot = 2;

    @Setup
    @Override
    public void prepare() {
        long common = 1;
        elements = new long[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = common;
        }
        elements[s - 1] = pivot;
    }

    @TearDown
    @Override
    public void clean() {
        elements = null;
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int findPosition() {
        int index = 0;
        while (pivot != elements[index]) {
            index++;
        }
        return index;
    }

    @Override
    public String getSimpleClassName() {
        return LongPrimitiveLookup.class.getSimpleName();
    }
}
