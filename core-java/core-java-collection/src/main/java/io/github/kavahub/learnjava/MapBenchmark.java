package io.github.kavahub.learnjava;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
 * {@link ConcurrentHashMap} 与 {@code Collections.synchronizedMap} 性能比较
 * 
 * <p>
 * 性能测试结果如下：
 * 
 * <pre>
 * Benchmark                                                Mode  Cnt  Score   Error   Units
 * MapBenchmark.A_randomReadAndWriteConcurrentHashMap  thrpt   10  3.726 ± 0.037  ops/ms
 * MapBenchmark.A_randomReadAndWriteSynchronizedMap    thrpt   10  3.741 ± 0.046  ops/ms
 * MapBenchmark.B_randomWriteConcurrentHashMap         thrpt   10  3.508 ± 0.077  ops/ms
 * MapBenchmark.B_randomWriteSynchronizedMap           thrpt   10  3.591 ± 0.148  ops/ms
 * MapBenchmark.C_randomReadConcurrentHashMap          thrpt   10  3.838 ± 0.306  ops/ms
 * MapBenchmark.C_randomReadSynchronizedMap            thrpt   10  3.976 ± 0.148  ops/ms
 * </pre>
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class MapBenchmark {
    private int TEST_NO_ITEMS;
    
    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(MapBenchmark.class.getSimpleName()).shouldFailOnError(true).threads(10).build();
        new Runner(options).run();
    }
    
    @Setup
    public void setup() {
        TEST_NO_ITEMS = 1000;
    }
    
    @Benchmark
    public void A_randomReadAndWriteSynchronizedMap() {
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());
        performReadAndWriteTest(map);
    }
    
    @Benchmark
    public void A_randomReadAndWriteConcurrentHashMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        performReadAndWriteTest(map);
    }
    
    @Benchmark
    public void B_randomWriteSynchronizedMap() {
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());
        performWriteTest(map);
    }
    
    @Benchmark
    public void B_randomWriteConcurrentHashMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        performWriteTest(map);
    }

    @Benchmark
    public void C_randomReadSynchronizedMap() {
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());
        performReadTest(map);
    }
    
    @Benchmark
    public void C_randomReadConcurrentHashMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        performReadTest(map);
    }
    
    private void performReadAndWriteTest(final Map<String, Integer> map) {
        for (int i = 0; i < TEST_NO_ITEMS; i++) {
            Integer randNumber = (int) Math.ceil(Math.random() * TEST_NO_ITEMS);
            map.get(String.valueOf(randNumber));
            map.put(String.valueOf(randNumber), randNumber);
        }
    }

    private void performWriteTest(final Map<String, Integer> map) {
        for (int i = 0; i < TEST_NO_ITEMS; i++) {
            Integer randNumber = (int) Math.ceil(Math.random() * TEST_NO_ITEMS);
            map.put(String.valueOf(randNumber), randNumber);
        }
    }
    
    private void performReadTest(final Map<String, Integer> map) {
        for (int i = 0; i < TEST_NO_ITEMS; i++) {
            Integer randNumber = (int) Math.ceil(Math.random() * TEST_NO_ITEMS);
            map.get(String.valueOf(randNumber));
        }
    } 
}
