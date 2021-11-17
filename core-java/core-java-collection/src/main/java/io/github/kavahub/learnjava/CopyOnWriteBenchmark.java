package io.github.kavahub.learnjava;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
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

// Benchmark                          Mode  Cnt      Score      Error  Units
// CopyOnWriteBenchmark.testAdd       avgt   10  15473.284 ± 5444.669  ns/op
// CopyOnWriteBenchmark.testAddAt     avgt   10  20829.218 ± 8049.494  ns/op
// CopyOnWriteBenchmark.testContains  avgt   10  40545.776 ± 1755.125  ns/op
// CopyOnWriteBenchmark.testGet       avgt   10     32.668 ±    1.604  ns/op
// CopyOnWriteBenchmark.testIndexOf   avgt   10  40771.039 ±  746.976  ns/op
// CopyOnWriteBenchmark.testRemove    avgt   10     25.190 ±    1.851  ns/op

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class CopyOnWriteBenchmark {
    @State(Scope.Thread)
    public static class MyState {

        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

        int iterations = 100000;

        Random random = new Random();
        Integer randomIndex;

        @Setup(Level.Trial)
        public void setUp() {
            for (int i = 0; i < iterations; i++) {
                copyOnWriteArrayList.add(i);
            }
        }

        @Setup(Level.Invocation)
        public void additionalSetup() {
            randomIndex = Integer.valueOf(random.nextInt(iterations));
        }
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(CopyOnWriteBenchmark.class.getSimpleName()).threads(1)
                .shouldFailOnError(true).build();
        new Runner(options).run();
    }

    @Benchmark
    public void testAdd(CopyOnWriteBenchmark.MyState state) {
        state.copyOnWriteArrayList.add(state.randomIndex);
    }

    @Benchmark
    public void testAddAt(CopyOnWriteBenchmark.MyState state) {
        state.copyOnWriteArrayList.add(state.randomIndex, state.randomIndex);
    }

    @Benchmark
    public boolean testContains(CopyOnWriteBenchmark.MyState state) {
        return state.copyOnWriteArrayList.contains(state.randomIndex);
    }

    @Benchmark
    public Integer testIndexOf(CopyOnWriteBenchmark.MyState state) {
        return state.copyOnWriteArrayList.indexOf(state.randomIndex);
    }

    @Benchmark
    public Integer testGet(CopyOnWriteBenchmark.MyState state) {
        return state.copyOnWriteArrayList.get(state.randomIndex);
    }

    @Benchmark
    public boolean testRemove(CopyOnWriteBenchmark.MyState state) {
        return state.copyOnWriteArrayList.remove(state.randomIndex);
    }

}
