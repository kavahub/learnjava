package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
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
 * Benchmark                                      Mode  Cnt      Score      Error   Units
 * ListBenchmark.benchmark01_arrayListAdd        thrpt   10  37071.421 ±  472.159  ops/ms
 * ListBenchmark.benchmark01_linkedListAdd       thrpt   10  32530.566 ±  872.869  ops/ms
 * ListBenchmark.benchmark02_arrayListContains   thrpt   10   2542.931 ±   26.388  ops/ms
 * ListBenchmark.benchmark02_linkedListContains  thrpt   10   1065.221 ±   33.670  ops/ms
 * ListBenchmark.benchmark03_arrayListRemove     thrpt   10  46655.488 ±  194.944  ops/ms
 * ListBenchmark.benchmark03_linkedListRemove    thrpt   10  48047.383 ±  274.248  ops/ms
 * ListBenchmark.benchmark04_arrayListAddAt      thrpt   10     15.476 ±    5.985  ops/ms
 * ListBenchmark.benchmark04_linkedListAddAt     thrpt   10    939.467 ±  234.178  ops/ms
 * </pre>
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class ListBenchmark {
        @State(Scope.Thread)
        public static class MyState {
                ArrayList<Integer> arrayList = new ArrayList<>();
                LinkedList<Integer> linkedList = new LinkedList<>();

                final int iterations = 1000;
                Random random = new Random();
                Integer randomIndex;

                @Setup(Level.Trial)
                public void setUp() {
                        for (int i = 0; i < iterations; i++) {
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

        @Benchmark
        public void benchmark01_arrayListAdd(ListBenchmark.MyState state) {
                // 避免OOM异常
                if (state.arrayList.size() >= state.iterations * 10) {
                        state.arrayList.clear();
                }
                state.arrayList.add(state.randomIndex);
        }

        @Benchmark
        public void benchmark01_linkedListAdd(ListBenchmark.MyState state) {
                if (state.linkedList.size() >= state.iterations * 10) {
                        state.linkedList.clear();
                }
                state.linkedList.add(state.randomIndex);
        }

        // contains

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
        public void benchmark03_arrayListRemove(ListBenchmark.MyState state) {
                state.arrayList.remove(state.randomIndex);
        }

        @Benchmark
        public void benchmark03_linkedListRemove(ListBenchmark.MyState state) {
                state.linkedList.remove(state.randomIndex);
        }

        // addAt

        @Benchmark
        public void benchmark04_arrayListAddAt(ListBenchmark.MyState state) {
                state.arrayList.add(state.randomIndex, state.randomIndex);
        }

        @Benchmark
        public void benchmark04_linkedListAddAt(ListBenchmark.MyState state) {
                state.linkedList.add(state.randomIndex, state.randomIndex);
        }
}
