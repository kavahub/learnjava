package io.github.kavahub.learnjava.boxing;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

/**
 * 布尔包装类型定位
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
public class BooleanWrapperLookup extends Lookup {
    private Boolean[] elements;
    private final boolean pivot = false;

    @Override
    @Setup
    public void prepare() {
        elements = new Boolean[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = true;
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
        Boolean pivotWrapper = pivot;
        while (!pivotWrapper.equals(elements[index])) {
            index++;
        }
        return index;

    }

    @Override
    public String getSimpleClassName() {
        return BooleanWrapperLookup.class.getSimpleName();
    }


}
