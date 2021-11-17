package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

// Benchmark                                     Mode  Cnt      Score     Error   Units
// SetVsListBenchmark.benchmark02_listContains  thrpt   10      8.855 ±   0.755  ops/ms
// SetVsListBenchmark.benchmark02_setContains   thrpt   10  11137.365 ± 200.158  ops/ms
// SetVsListBenchmark.benchmark05_listRemove    thrpt   10     20.131 ±  15.004  ops/ms
// SetVsListBenchmark.benchmark05_setRemove     thrpt   10  11178.329 ± 141.819  ops/ms
// SetVsListBenchmark.benchmark06_listAdd       thrpt   10  27961.016 ± 704.714  ops/ms
// SetVsListBenchmark.benchmark06_setAdd        thrpt   10   8757.738 ± 296.209  ops/ms


@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class SetVsListBenchmark {
    @State(Scope.Thread)
    public static class MyState {
        private Set<Employee> set = new HashSet<>();
        private List<Employee> list = new ArrayList<>();

        Random random = new Random();
        int iterations = 100000;
        int randomIndex = -1;

        @Setup(Level.Trial)
        public void setUp() {

            for (int i = 0; i < iterations; i++) {
                set.add(new Employee(i, "John"));
                list.add(new Employee(i, "John"));
            }
        }

        @Setup(Level.Invocation)
        public void additionalSetup() {
            randomIndex = random.nextInt(iterations);
        }
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(SetVsListBenchmark.class.getSimpleName())
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public void benchmark02_listContains(SetVsListBenchmark.MyState state) {
        state.list.contains(new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark02_setContains(SetVsListBenchmark.MyState state) {
        state.set.contains(new Employee(state.randomIndex, "John"));
    }
    
    
    @Benchmark
    public void benchmark05_listRemove(SetVsListBenchmark.MyState state) {
        state.list.remove(new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark05_setRemove(SetVsListBenchmark.MyState state) {
        state.set.remove(new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark06_listAdd(SetVsListBenchmark.MyState state) {
        // 避免添加太多的对象，造成内存溢出
        final int size = state.list.size();
        if (size > state.iterations * 2) {
            state.list.clear();
        }
        state.list.add(new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark06_setAdd(SetVsListBenchmark.MyState state) {
        // 避免添加太多的对象，造成内存溢出
        final int size = state.set.size();
        if (size > state.iterations * 2) {
            state.set.clear();
        }
        state.set.add(new Employee(state.randomIndex, "John"));
    }



}
