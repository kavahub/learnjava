package io.github.kavahub.learnjava;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
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

// Benchmark                                                 Mode  Cnt    Score     Error  Units
// HashMapBenchmark.benchmark01_concurrentHashMapGet         avgt   10   67.974 ±  15.285  ns/op
// HashMapBenchmark.benchmark01_concurrentSkipListMapGet     avgt   10  355.361 ±   6.634  ns/op
// HashMapBenchmark.benchmark01_hashMapGet                   avgt   10   84.301 ±  33.591  ns/op
// HashMapBenchmark.benchmark01_identityHashMapGet           avgt   10   90.121 ±  20.177  ns/op
// HashMapBenchmark.benchmark01_linkedHashMapGet             avgt   10   77.602 ±   9.472  ns/op
// HashMapBenchmark.benchmark01_weakHashMapGet               avgt   10   71.934 ±  12.635  ns/op
// HashMapBenchmark.benchmark02_concurrentHashMapPut         avgt   10  194.111 ±  26.315  ns/op
// HashMapBenchmark.benchmark02_concurrentSkipListMapPut     avgt   10  576.933 ± 142.144  ns/op
// HashMapBenchmark.benchmark02_hashMapPut                   avgt   10  185.656 ±  16.484  ns/op
// HashMapBenchmark.benchmark02_identityHashMapPut           avgt   10  738.552 ± 588.374  ns/op
// HashMapBenchmark.benchmark02_linkedHashMapPut             avgt   10  181.679 ±  20.413  ns/op
// HashMapBenchmark.benchmark02_weakHashMapPut               avgt   10  192.679 ±  24.778  ns/op
// HashMapBenchmark.benchmark03_concurrentHashMapRemove      avgt   10   29.346 ±   1.658  ns/op
// HashMapBenchmark.benchmark03_concurrentSkipListMapRemove  avgt   10   31.352 ±   1.205  ns/op
// HashMapBenchmark.benchmark03_hashMapRemove                avgt   10   28.614 ±   2.309  ns/op
// HashMapBenchmark.benchmark03_identityHashMapRemove        avgt   10   87.536 ±   9.085  ns/op
// HashMapBenchmark.benchmark03_linkedHashMapRemove          avgt   10   30.655 ±   3.919  ns/op
// HashMapBenchmark.benchmark03_weakHashMapRemove            avgt   10   31.368 ±   1.242  ns/op


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class HashMapBenchmark {
    @State(Scope.Thread)
    public static class MyState {

        Map<Integer, Integer> hashMap = new HashMap<>();
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        IdentityHashMap<Integer, Integer> identityHashMap = new IdentityHashMap<>();
        WeakHashMap<Integer, Integer> weakHashMap = new WeakHashMap<>();
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        ConcurrentSkipListMap<Integer, Integer> concurrentSkipListMap = new ConcurrentSkipListMap<>();

        // TreeMap

        int iterations = 100000;
        Random random = new Random();
        int randomIndex;

        @Setup(Level.Trial)
        public void setUp() {
            for (int i = 0; i < iterations; i++) {
                hashMap.put(i, i);
                linkedHashMap.put(i, i);
                identityHashMap.put(i, i);
                weakHashMap.put(i, i);
                concurrentHashMap.put(i, i);
                concurrentSkipListMap.put(i, i);
            }

        }

        @Setup(Level.Invocation)
        public void additionalSetup() {
            randomIndex = random.nextInt(iterations);
        }
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(HashMapBenchmark.class.getSimpleName()).shouldFailOnError(true)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public Integer benchmark01_hashMapGet(HashMapBenchmark.MyState state) {
        return state.hashMap.get(state.randomIndex);
    }

    @Benchmark
    public Integer benchmark01_linkedHashMapGet(HashMapBenchmark.MyState state) {
        return state.linkedHashMap.get(state.randomIndex);
    }

    @Benchmark
    public Integer benchmark01_identityHashMapGet(HashMapBenchmark.MyState state) {
        return state.identityHashMap.get(state.randomIndex);
    }

    @Benchmark
    public Integer benchmark01_weakHashMapGet(HashMapBenchmark.MyState state) {
        return state.weakHashMap.get(state.randomIndex);
    }

    @Benchmark
    public Integer benchmark01_concurrentHashMapGet(HashMapBenchmark.MyState state) {
        return state.concurrentHashMap.get(state.randomIndex);
    }

    @Benchmark
    public Integer benchmark01_concurrentSkipListMapGet(HashMapBenchmark.MyState state) {
        return state.concurrentSkipListMap.get(state.randomIndex);
    }

    // put

    @Benchmark
    public Integer benchmark02_hashMapPut(HashMapBenchmark.MyState state) {
        return state.hashMap.put(state.randomIndex, state.randomIndex);
    }

    @Benchmark
    public Integer benchmark02_linkedHashMapPut(HashMapBenchmark.MyState state) {
        return state.linkedHashMap.put(state.randomIndex, state.randomIndex);
    }

    @Benchmark
    public Integer benchmark02_identityHashMapPut(HashMapBenchmark.MyState state) {
        return state.identityHashMap.put(state.randomIndex, state.randomIndex);
    }

    @Benchmark
    public Integer benchmark02_weakHashMapPut(HashMapBenchmark.MyState state) {
        return state.weakHashMap.put(state.randomIndex, state.randomIndex);
    }

    @Benchmark
    public Integer benchmark02_concurrentHashMapPut(HashMapBenchmark.MyState state) {
        return state.concurrentHashMap.put(state.randomIndex, state.randomIndex);
    }

    @Benchmark
    public Integer benchmark02_concurrentSkipListMapPut(HashMapBenchmark.MyState state) {
        return state.concurrentSkipListMap.put(state.randomIndex, state.randomIndex);
    }

    // remove

    @Benchmark
    public Integer benchmark03_hashMapRemove(HashMapBenchmark.MyState state) {
        return state.hashMap.remove(state.randomIndex);
    }

    @Benchmark
    public Integer benchmark03_linkedHashMapRemove(HashMapBenchmark.MyState state) {
        return state.linkedHashMap.remove(state.randomIndex);
    }

    @Benchmark
    public Integer benchmark03_identityHashMapRemove(HashMapBenchmark.MyState state) {
        return state.identityHashMap.remove(state.randomIndex);
    }

    @Benchmark
    public Integer benchmark03_weakHashMapRemove(HashMapBenchmark.MyState state) {
        return state.weakHashMap.remove(state.randomIndex);
    }

    @Benchmark
    public Integer benchmark03_concurrentHashMapRemove(HashMapBenchmark.MyState state) {
        return state.concurrentHashMap.remove(state.randomIndex);
    }

    @Benchmark
    public Integer benchmark03_concurrentSkipListMapRemove(HashMapBenchmark.MyState state) {
        return state.concurrentSkipListMap.remove(state.randomIndex);
    }

}
