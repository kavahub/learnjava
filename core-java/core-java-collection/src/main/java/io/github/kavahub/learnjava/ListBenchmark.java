package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.LinkedList;
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

// Benchmark                                               Mode  Cnt     Score     Error  Units
// ListBenchmark.benchmark01_arrayListAdd                  avgt   10    28.288 ±   1.242  ns/op
// ListBenchmark.benchmark01_copyOnWriteArrayListAdd       avgt   10   165.258 ±   1.925  ns/op
// ListBenchmark.benchmark01_linkedListAdd                 avgt   10    30.992 ±   0.195  ns/op
// ListBenchmark.benchmark02_arrayListContains             avgt   10   390.358 ±   4.695  ns/op
// ListBenchmark.benchmark02_copyOnWriteArrayListContains  avgt   10   420.651 ±  20.872  ns/op
// ListBenchmark.benchmark02_linkedListContains            avgt   10  1034.986 ± 125.845  ns/op
// ListBenchmark.benchmark03_arrayListRemove               avgt   10    24.147 ±   2.919  ns/op
// ListBenchmark.benchmark03_copyOnWriteArrayListRemove    avgt   10    24.252 ±   1.712  ns/op
// ListBenchmark.benchmark03_linkedListRemove              avgt   10    21.604 ±   1.494  ns/op

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class ListBenchmark {

        @State(Scope.Thread)
        public static class MyState {

                CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
                ArrayList<Integer> arrayList = new ArrayList<>();
                LinkedList<Integer> linkedList = new LinkedList<>();

                final int iterations = 1000;
                Random random = new Random();
                Integer randomIndex;

                @Setup(Level.Trial)
                public void setUp() {
                        for (int i = 0; i < iterations; i++) {
                                copyOnWriteArrayList.add(i);
                                arrayList.add(i);
                                linkedList.add(i);
                        }
                }

                @Setup(Level.Invocation)
                public void additionalSetup() {
                        randomIndex = Integer.valueOf(random.nextInt(iterations));
                }
        }

        public static void main(String[] args) throws Exception {
                Options options = new OptionsBuilder().include(ListBenchmark.class.getSimpleName())
                                .shouldFailOnError(true).build();
                new Runner(options).run();
        }

        // add
        // 需要清空集合，因方法调用太快，造成java.lang.OutOfMemoryError: Java heap space
        @Benchmark
        public void benchmark01_copyOnWriteArrayListAdd(ListBenchmark.MyState state) {
                if (state.copyOnWriteArrayList.size() >= state.iterations) {
                        state.copyOnWriteArrayList.clear();
                }
                state.copyOnWriteArrayList.add(state.randomIndex);
        }

        @Benchmark
        public void benchmark01_arrayListAdd(ListBenchmark.MyState state) {
                if (state.arrayList.size() >= state.iterations) {
                        state.arrayList.clear();
                }
                state.arrayList.add(state.randomIndex);
        }

        @Benchmark
        public void benchmark01_linkedListAdd(ListBenchmark.MyState state) {
                if (state.linkedList.size() >= state.iterations) {
                        state.linkedList.clear();
                }
                state.linkedList.add(state.randomIndex);
        }

        // contains

        @Benchmark
        public void benchmark02_copyOnWriteArrayListContains(ListBenchmark.MyState state) {
                state.copyOnWriteArrayList.contains(state.randomIndex);
        }

        @Benchmark
        public void benchmark02_arrayListContains(ListBenchmark.MyState state) {
                state.arrayList.contains(state.randomIndex);
        }

        @Benchmark
        public void benchmark02_linkedListContains(ListBenchmark.MyState state) {
                state.linkedList.contains(state.randomIndex);
        }

        // remove

        @Benchmark
        public void benchmark03_copyOnWriteArrayListRemove(ListBenchmark.MyState state) {
                state.copyOnWriteArrayList.remove(state.randomIndex);
        }

        @Benchmark
        public void benchmark03_arrayListRemove(ListBenchmark.MyState state) {
                state.arrayList.remove(state.randomIndex);
        }

        @Benchmark
        public void benchmark03_linkedListRemove(ListBenchmark.MyState state) {
                state.linkedList.remove(state.randomIndex);
        }

}
