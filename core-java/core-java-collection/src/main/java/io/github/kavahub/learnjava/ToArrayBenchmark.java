package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


/**
 * 集合转换成数组性能比较
 * 
 * <p>
 * 性能测试结果如下：
 * 
 * <pre>
 * Benchmark                      (size)      (type)  Mode  Cnt          Score         Error  Units
 * ToArrayBenchmark.pre_sized         10  array-list  avgt   15         23.255 ±       0.999  ns/op
 * ToArrayBenchmark.pre_sized         10    tree-set  avgt   15         41.389 ±       2.691  ns/op
 * ToArrayBenchmark.pre_sized      10000  array-list  avgt   15      15681.750 ±    2280.087  ns/op
 * ToArrayBenchmark.pre_sized      10000    tree-set  avgt   15      66209.705 ±    8058.096  ns/op
 * ToArrayBenchmark.pre_sized   10000000  array-list  avgt   15   36983644.574 ± 1395242.314  ns/op
 * ToArrayBenchmark.pre_sized   10000000    tree-set  avgt   15  106445661.333 ± 1816839.813  ns/op
 * ToArrayBenchmark.zero_sized        10  array-list  avgt   15         23.004 ±       1.540  ns/op
 * ToArrayBenchmark.zero_sized        10    tree-set  avgt   15         46.747 ±       5.061  ns/op
 * ToArrayBenchmark.zero_sized     10000  array-list  avgt   15      13253.337 ±     875.199  ns/op
 * ToArrayBenchmark.zero_sized     10000    tree-set  avgt   15      65072.441 ±    4715.718  ns/op
 * ToArrayBenchmark.zero_sized  10000000  array-list  avgt   15   36757374.199 ± 1342328.814  ns/op
 * ToArrayBenchmark.zero_sized  10000000    tree-set  avgt   15  107969508.741 ± 3995345.237  ns/op
 * </pre>
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1, jvmArgsAppend = { "-XX:+UseParallelGC", "-Xms4g", "-Xmx4g" })
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class ToArrayBenchmark {
    @Param({ "10", "10000", "10000000" })
    private int size;

    @Param({ "array-list", "tree-set" })
    private String type;

    private Collection<String> collection;

    @Setup
    public void setup() {
        switch (type) {
        case "array-list":
            collection = new ArrayList<String>();
            break;
        case "tree-set":
            collection = new TreeSet<String>();
            break;
        default:
            throw new UnsupportedOperationException();
        }
        for (int i = 0; i < size; i++) {
            collection.add(String.valueOf(i));
        }
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(ToArrayBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .build();
        new Runner(options).run();
    }

    @Benchmark
    public String[] zero_sized() {
        return collection.toArray(new String[0]);
    }

    @Benchmark
    public String[] pre_sized() {
        return collection.toArray(new String[collection.size()]);
    }
}
