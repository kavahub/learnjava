package io.github.kavahub.learnjava;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
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
 * 性能测试结果如下：
 * 
 * <pre>
 * Benchmark                                   Mode  Cnt   Score   Error  Units
 * SetBenchmark.benchmark01_HashSetContains    avgt   10  26.160 ± 1.540  ns/op
 * SetBenchmark.benchmark01_LinkedSetContains  avgt   10  28.083 ± 2.892  ns/op
 * SetBenchmark.benchmark02_HashSetRemove      avgt   10  28.058 ± 2.489  ns/op
 * SetBenchmark.benchmark02_LinkedSetRemove    avgt   10  31.701 ± 3.367  ns/op
 * SetBenchmark.benchmark03_HashSetAdd         avgt   10  37.129 ± 3.821  ns/op
 * SetBenchmark.benchmark03_LinkedSetAdd       avgt   10  40.015 ± 5.140  ns/op
 * </pre>
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class SetBenchmark {
    @State(Scope.Thread)
    public static class MyState {

        Set<Integer> employeeHashSet = new HashSet<>();
        // LinkedHashSet是Set集合的一个实现，具有set集合不重复的特点，同时具有可预测的迭代顺序，也就是我们插入的顺序
        Set<Integer> employeeLinkedSet = new LinkedHashSet<>();
        //ConcurrentSkipListSet<Employee> employeeSet = new ConcurrentSkipListSet <>();

        // TreeSet 

 
        int iterations = 1000;
        Random random = new Random();
        int randomIndex;

        @Setup(Level.Trial)
        public void setUp() {
            for (int i = 0; i < iterations; i++) {
                employeeLinkedSet.add(i);
                employeeHashSet.add(i);
            }
        }

        @Setup(Level.Invocation)
        public void additionalSetup() {
            randomIndex = random.nextInt(iterations);
        }
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(SetBenchmark.class.getSimpleName()).build();
        new Runner(options).run();
    }

    @Benchmark
    public Boolean benchmark01_HashSetContains(SetBenchmark.MyState state) {
        return state.employeeHashSet.contains(state.randomIndex);
    }

    @Benchmark
    public Boolean  benchmark01_LinkedSetContains(SetBenchmark.MyState state) {
        return state.employeeLinkedSet.contains(state.randomIndex);
    }

    @Benchmark
    public Boolean benchmark02_HashSetRemove(SetBenchmark.MyState state) {
        return state.employeeHashSet.remove(state.randomIndex);
    }

    @Benchmark
    public Boolean  benchmark02_LinkedSetRemove(SetBenchmark.MyState state) {
        return state.employeeLinkedSet.remove(state.randomIndex);
    }

    @Benchmark
    public Boolean benchmark03_HashSetAdd(SetBenchmark.MyState state) {
        return state.employeeHashSet.add(state.randomIndex);
    }

    @Benchmark
    public Boolean  benchmark03_LinkedSetAdd(SetBenchmark.MyState state) {
        return state.employeeLinkedSet.add(state.randomIndex);
    }

}
