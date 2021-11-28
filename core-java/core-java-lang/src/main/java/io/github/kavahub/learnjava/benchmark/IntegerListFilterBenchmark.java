package io.github.kavahub.learnjava.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;
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
 * 集合过滤性能测试
 * 
 * <p>
 * 测试结果：
 * 
 * <pre>
 * Benchmark                                         Mode  Cnt    Score    Error  Units
 * IntegerListFilterBenchmark.ecMutableList          thrpt   10  165.221 ± 11.364  ops/s
 * IntegerListFilterBenchmark.ecMutableListParallel  thrpt   10  314.834 ± 11.407  ops/s
 * IntegerListFilterBenchmark.ecPrimitive            thrpt   10  201.279 ±  3.023  ops/s
 * IntegerListFilterBenchmark.ecPrimitiveParallel    thrpt   10  604.697 ± 71.280  ops/s
 * IntegerListFilterBenchmark.jdkList                thrpt   10  131.126 ± 13.174  ops/s
 * IntegerListFilterBenchmark.jdkListParallel        thrpt   10  350.679 ± 33.511  ops/s
 * </pre>
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class IntegerListFilterBenchmark {
    private List<Integer> jdkIntList;
    private MutableList<Integer> ecMutableList;
    private IntList ecIntList;
    private ExecutorService executor;

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder().include(IntegerListFilterBenchmark.class.getSimpleName()).build();

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
    public List<Integer> jdkList() {
        return jdkIntList.stream().filter(i -> i % 5 == 0).collect(Collectors.toList());
    }

    @Benchmark
    public MutableList<Integer> ecMutableList() {
        return ecMutableList.select(i -> i % 5 == 0);
    }

    @Benchmark
    public List<Integer> jdkListParallel() {
        return jdkIntList.parallelStream().filter(i -> i % 5 == 0).collect(Collectors.toList());
    }

    @Benchmark
    public MutableList<Integer> ecMutableListParallel() {
        return ecMutableList.asParallel(executor, 100_000).select(i -> i % 5 == 0).toList();
    }

    @Benchmark
    public IntList ecPrimitive() {
        return this.ecIntList.select(i -> i % 5 == 0);
    }

    @Benchmark
    public IntList ecPrimitiveParallel() {
        return this.ecIntList.primitiveParallelStream().filter(i -> i % 5 == 0).collect(IntLists.mutable::empty,
                MutableIntList::add, MutableIntList::addAll);
    }
}
