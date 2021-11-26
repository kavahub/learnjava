package io.github.kavahub.learnjava.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


/**
 * 过滤器性能测试。
 * 
 * <p>
 * 测试结果如下：
 * 
 * <pre>
 * Benchmark                                Mode  Cnt    Score    Error  Units
 * FilterBenchmark.filterWithApacheCommons  avgt   10   34.265 ± 34.154  us/op
 * FilterBenchmark.filterWithEclipse        avgt   10  131.178 ±  6.559  us/op
 * FilterBenchmark.filterWithGuava          avgt   10    0.005 ±  0.001  us/op
 * FilterBenchmark.filterWithStream         avgt   10  102.078 ±  1.413  us/op
 * </pre>
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class FilterBenchmark {
    private static final Set<Integer> target = new HashSet<Integer>();
    private static final int MAX_SIZE = 10000;

    @Setup
    public void setUp() {
        for (int i = 0; i < MAX_SIZE; i++) {
            target.add(ThreadLocalRandom.current().nextInt(MAX_SIZE));
        }
    }

    
    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(FilterBenchmark.class.getSimpleName()).shouldFailOnError(true)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public Collection<Integer> filterWithApacheCommons() {
        return FilterWithApacheCommons.findEvenNumbers(target);
    }

    @Benchmark
    public Collection<Integer> filterWithEclipse() {
        return FilterWithEclipse.findEvenNumbers(target);
    }

    @Benchmark
    public Collection<Integer> filterWithGuava() {
        return FilterWithGuava.findEvenNumbers(target);
    }

    @Benchmark
    public Collection<Integer> filterWithStream() {
        return FilterWithStream.findEvenNumbers(target);
    }
}
