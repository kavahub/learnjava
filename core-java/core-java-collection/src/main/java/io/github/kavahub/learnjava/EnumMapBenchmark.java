package io.github.kavahub.learnjava;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;
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

// Benchmark                                          Mode  Cnt    Score    Error  Units
// EnumMapBenchmark.benchmark01_EnumMapPut            avgt   10   36.281 ±  0.444  ns/op
// EnumMapBenchmark.benchmark01_HashMapPut            avgt   10   52.150 ±  0.608  ns/op
// EnumMapBenchmark.benchmark01_TreeMapPut            avgt   10   81.786 ±  3.421  ns/op
// EnumMapBenchmark.benchmark02_EnumMapGet            avgt   10   31.808 ±  2.573  ns/op
// EnumMapBenchmark.benchmark02_HashMapGet            avgt   10   33.764 ±  1.122  ns/op
// EnumMapBenchmark.benchmark02_TreeMapGet            avgt   10   54.572 ±  5.301  ns/op
// EnumMapBenchmark.benchmark03_EnumMapContainsKey    avgt   10   30.539 ±  1.371  ns/op
// EnumMapBenchmark.benchmark03_HashMapContainsKey    avgt   10   34.139 ±  2.917  ns/op
// EnumMapBenchmark.benchmark03_TreeMapContainsKey    avgt   10   53.172 ±  6.916  ns/op
// EnumMapBenchmark.benchmark04_EnumMapContainsValue  avgt   10  125.295 ± 13.547  ns/op
// EnumMapBenchmark.benchmark04_HashMapContainsValue  avgt   10  204.785 ± 11.730  ns/op
// EnumMapBenchmark.benchmark04_TreeMapContainsValue  avgt   10  184.699 ± 33.364  ns/op

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class EnumMapBenchmark {

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(EnumMapBenchmark.class.getSimpleName()).threads(1).forks(0)
                .shouldFailOnError(true).shouldDoGC(false).jvmArgs("-server").build();
        new Runner(options).run();
    }

    @State(Scope.Thread)
    public static class BenchmarkState {
        EnumMap<DummyEnum, String> enumMap = new EnumMap<>(DummyEnum.class);
        HashMap<DummyEnum, String> hashMap = new HashMap<>();
        TreeMap<DummyEnum, String> treeMap = new TreeMap<>();
        int len = DummyEnum.values().length;
        Random random = new Random();
        int randomIndex;

        @Setup(Level.Trial)
        public void setUp() {
            DummyEnum[] values = DummyEnum.values();
            for (int i = 0; i < len; i++) {
                enumMap.put(values[i], values[i].toString());
                hashMap.put(values[i], values[i].toString());
                treeMap.put(values[i], values[i].toString());
            }
        }

        @Setup(Level.Invocation)
        public void additionalSetup() {
            randomIndex = random.nextInt(len);
        }

    }

    @Benchmark
    public int benchmark01_EnumMapPut(BenchmarkState s) {
        s.enumMap.put(DummyEnum.values()[s.randomIndex], DummyEnum.values()[s.randomIndex].toString());
        return ++s.randomIndex;
    }

    @Benchmark
    public int benchmark01_HashMapPut(BenchmarkState s) {
        s.hashMap.put(DummyEnum.values()[s.randomIndex], DummyEnum.values()[s.randomIndex].toString());
        return ++s.randomIndex;
    }

    @Benchmark
    public int benchmark01_TreeMapPut(BenchmarkState s) {
        s.treeMap.put(DummyEnum.values()[s.randomIndex], DummyEnum.values()[s.randomIndex].toString());
        return ++s.randomIndex;
    }

    @Benchmark
    public int benchmark02_EnumMapGet(BenchmarkState s) {
        s.enumMap.get(DummyEnum.values()[s.randomIndex]);
        return ++s.randomIndex;
    }

    @Benchmark
    public int benchmark02_HashMapGet(BenchmarkState s) {
        s.hashMap.get(DummyEnum.values()[s.randomIndex]);
        return ++s.randomIndex;
    }

    @Benchmark
    public int benchmark02_TreeMapGet(BenchmarkState s) {
        s.treeMap.get(DummyEnum.values()[s.randomIndex]);
        return ++s.randomIndex;
    }

    @Benchmark
    public int benchmark03_EnumMapContainsKey(BenchmarkState s) {
        s.enumMap.containsKey(DummyEnum.values()[s.randomIndex]);
        return ++s.randomIndex;
    }

    @Benchmark
    public int benchmark03_HashMapContainsKey(BenchmarkState s) {
        s.hashMap.containsKey(DummyEnum.values()[s.randomIndex]);
        return ++s.randomIndex;
    }

    @Benchmark
    public int benchmark03_TreeMapContainsKey(BenchmarkState s) {
        s.treeMap.containsKey(DummyEnum.values()[s.randomIndex]);
        return ++s.randomIndex;
    }

    @Benchmark
    public int benchmark04_EnumMapContainsValue(BenchmarkState s) {
        s.enumMap.containsValue(DummyEnum.values()[s.randomIndex].toString());
        return ++s.randomIndex;
    }

    @Benchmark
    public int benchmark04_HashMapContainsValue(BenchmarkState s) {
        s.hashMap.containsValue(DummyEnum.values()[s.randomIndex].toString());
        return ++s.randomIndex;
    }

    @Benchmark
    public int benchmark04_TreeMapContainsValue(BenchmarkState s) {
        s.treeMap.containsValue(DummyEnum.values()[s.randomIndex].toString());
        return ++s.randomIndex;
    }

    public enum DummyEnum {
        CCC_000, CCC_001, CCC_002, CCC_003, CCC_004, CCC_005, CCC_006, CCC_007, CCC_008, CCC_009, CCC_010, CCC_011,
        CCC_012, CCC_013, CCC_014, CCC_015, CCC_016, CCC_017, CCC_018, CCC_019, CCC_020, CCC_021, CCC_022, CCC_023,
        CCC_024, CCC_025, CCC_026, CCC_027, CCC_028, CCC_029, CCC_030, CCC_031, CCC_032, CCC_033, CCC_034, CCC_035,
        CCC_036, CCC_037, CCC_038, CCC_039, CCC_040, CCC_041, CCC_042, CCC_043, CCC_044, CCC_045, CCC_046, CCC_047,
        CCC_048, CCC_049,
    }

}
