package io.github.kavahub.learnjava.boxing;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

/**
 * 布尔基础类型定位
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
public class BooleanPrimitiveLookup extends Lookup {

    private boolean[] elements;
    private final boolean pivot = false;

    @Setup
    @Override
    public void prepare() {
        elements = new boolean[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = true;
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
        return BooleanPrimitiveLookup.class.getSimpleName();
    }



}
