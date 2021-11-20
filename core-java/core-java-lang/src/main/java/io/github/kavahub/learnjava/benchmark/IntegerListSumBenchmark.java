package io.github.kavahub.learnjava.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
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
 * 集合合计性能测试
 * 
 * <p>
 * 测试结果：
 * 
 * <pre>
 * Benchmark                                      Mode  Cnt     Score     Error  Units
 * IntegerListSumBenchmark.ecMutableList          thrpt   10   812.034 ±  91.526  ops/s
 * IntegerListSumBenchmark.ecMutableListParallel  thrpt   10  1370.108 ± 130.795  ops/s
 * IntegerListSumBenchmark.ecPrimitive            thrpt   10  2494.392 ± 102.111  ops/s
 * IntegerListSumBenchmark.ecPrimitiveParallel    thrpt   10  1106.251 ±  19.286  ops/s
 * IntegerListSumBenchmark.jdkList                thrpt   10   181.351 ±  15.635  ops/s
 * IntegerListSumBenchmark.jdkListParallel        thrpt   10  1439.250 ±  87.989  ops/s
 * </pre>
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class IntegerListSumBenchmark {
    private List<Integer> jdkIntList;
    private MutableList<Integer> ecMutableList;
    private ExecutorService executor;
    private IntList ecIntList;

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder().include(IntegerListSumBenchmark.class.getSimpleName()).build();

        new Runner(opts).run();
    }

    @Setup
    public void setup() {
        PrimitiveIterator.OfInt iterator = new Random(1L).ints(-10000, 10000).iterator();
        ecMutableList = FastList.newWithNValues(1_000_000, iterator::nextInt);
        jdkIntList = new ArrayList<>(1_000_000);
        jdkIntList.addAll(ecMutableList);
        ecIntList = ecMutableList.collectInt(i -> i, new IntArrayList(1_000_000));
        executor = Executors.newWorkStealingPool();
    }

    @Benchmark
    public long jdkList() {
        return jdkIntList.stream().mapToLong(i -> i).sum();
    }

    @Benchmark
    public long ecMutableList() {
        return ecMutableList.sumOfInt(i -> i);
    }

    @Benchmark
    public long jdkListParallel() {
        return jdkIntList.parallelStream().mapToLong(i -> i).sum();
    }

    @Benchmark
    public long ecMutableListParallel() {
        return ecMutableList.asParallel(executor, 100_000).sumOfInt(i -> i);
    }

    @Benchmark
    public long ecPrimitive() {
        return this.ecIntList.sum();
    }

    @Benchmark
    public long ecPrimitiveParallel() {
        return this.ecIntList.primitiveParallelStream().sum();
    }
}
