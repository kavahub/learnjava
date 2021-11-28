package io.github.kavahub.learnjava.boxing;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

/**
 * 短整型包装类型定位
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
public class ShortWrapperLookup extends Lookup {
    private Short[] elements;
    private final short pivot = 2;

    @Override
    @Setup
    public void prepare() {
        short common = 1;
        elements = new Short[s];
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
        Short pivotWrapper = pivot;
        while (!pivotWrapper.equals(elements[index])) {
            index++;
        }
        return index;

    }

    @Override
    public String getSimpleClassName() {
        return ShortWrapperLookup.class.getSimpleName();
    }


}
