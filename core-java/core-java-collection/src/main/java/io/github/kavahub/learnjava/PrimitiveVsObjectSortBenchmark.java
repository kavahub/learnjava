package io.github.kavahub.learnjava;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


/**
 * 性能测试结果如下：
 * 
 * <pre>
 * Benchmark                                                  Mode  Cnt  Score   Error  Units
 * PrimitiveVsObjectSortBenchmark.benchmarkArraysIntSort      avgt   10  1.476 ± 0.027  ms/op
 * PrimitiveVsObjectSortBenchmark.benchmarkArraysIntegerSort  avgt   10  4.904 ± 0.130  m
 * </pre>
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(batchSize = 100000, iterations = 10, time = 1)
@Warmup(batchSize = 100000, iterations = 1)
@Fork(1)
public class PrimitiveVsObjectSortBenchmark {
    @State(Scope.Thread)
    public static class Initialize {
        Integer[] numbers = { -769214442, -1283881723, 1504158300, -1260321086, -1800976432, 1278262737, 1863224321, 1895424914, 2062768552, -1051922993, 751605209, -1500919212, 2094856518, -1014488489, -931226326, -1677121986, -2080561705, 562424208, -1233745158, 41308167 };
        int[] primitives = { -769214442, -1283881723, 1504158300, -1260321086, -1800976432, 1278262737, 1863224321, 1895424914, 2062768552, -1051922993, 751605209, -1500919212, 2094856518, -1014488489, -931226326, -1677121986, -2080561705, 562424208, -1233745158, 41308167 };
    }

    @Benchmark
    public Integer[] benchmarkArraysIntegerSort(PrimitiveVsObjectSortBenchmark.Initialize state) {
        Arrays.sort(state.numbers);
        return state.numbers;
    }

    @Benchmark
    public int[] benchmarkArraysIntSort(PrimitiveVsObjectSortBenchmark.Initialize state) {
        Arrays.sort(state.primitives);
        return state.primitives;
    }


    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(PrimitiveVsObjectSortBenchmark.class.getSimpleName()).threads(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
