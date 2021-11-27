package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
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
 * {@link ArrayList} 性能整体优于 {@link Vector}
 * 
 * <p>
 * 性能测试结果如下:
 * 
 * <pre>
 * Benchmark                                                  Mode  Cnt      Score      Error   Units
 * ArrayListVsVectorBenchmark.benchmark01_arrayListAddAt     thrpt   10     27.246 ±    6.536  ops/ms
 * ArrayListVsVectorBenchmark.benchmark01_vectorAddAt        thrpt   10     27.648 ±    8.677  ops/ms
 * ArrayListVsVectorBenchmark.benchmark02_arrayListContains  thrpt   10      9.715 ±    0.898  ops/ms
 * ArrayListVsVectorBenchmark.benchmark02_vectorContains     thrpt   10      8.483 ±    0.860  ops/ms
 * ArrayListVsVectorBenchmark.benchmark03_arrayListIndexOf   thrpt   10     10.520 ±    0.538  ops/ms
 * ArrayListVsVectorBenchmark.benchmark03_vectorIndexOf      thrpt   10      8.982 ±    1.029  ops/ms
 * ArrayListVsVectorBenchmark.benchmark04_arrayListGet       thrpt   10  95283.917 ± 3890.007  ops/ms
 * ArrayListVsVectorBenchmark.benchmark04_vectorGet          thrpt   10  93292.957 ± 6353.712  ops/ms
 * ArrayListVsVectorBenchmark.benchmark05_arrayListRemove    thrpt   10      6.452 ±    1.312  ops/ms
 * ArrayListVsVectorBenchmark.benchmark05_vectorRemove       thrpt   10      6.622 ±    0.773  ops/ms
 * ArrayListVsVectorBenchmark.benchmark06_arrayListAdd       thrpt   10  61722.974 ± 3527.918  ops/ms
 * ArrayListVsVectorBenchmark.benchmark06_vectorAdd          thrpt   10  35211.406 ± 1932.546  ops/ms
 * </pre>
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class ArrayListVsVectorBenchmark {

    @State(Scope.Thread)
    public static class MyState {

        List<Employee> arrayList = new ArrayList<>();
        Vector<Employee> vector = new Vector<>();

        Random random = new Random();
        int iterations = 100000;
        int randomIndex = -1;

        @Setup(Level.Trial)
        public void setUp() {
            for (int i = 0; i < iterations; i++) {
                arrayList.add(new Employee(i, "John"));
                vector.add(new Employee(i, "John"));
            }
        }

        @Setup(Level.Invocation)
        public void additionalSetup() {
            randomIndex = random.nextInt(iterations);
        }
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(ArrayListVsVectorBenchmark.class.getSimpleName()).threads(3)
                .shouldFailOnError(true).build();
        new Runner(options).run();
    }

    @Benchmark
    public void benchmark01_arrayListAddAt(ArrayListVsVectorBenchmark.MyState state) {
        state.arrayList.add(state.randomIndex, new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark01_vectorAddAt(ArrayListVsVectorBenchmark.MyState state) {
        state.vector.add(state.randomIndex, new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark02_arrayListContains(ArrayListVsVectorBenchmark.MyState state) {
        state.arrayList.contains(new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark02_vectorContains(ArrayListVsVectorBenchmark.MyState state) {
        state.vector.contains(new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark03_arrayListIndexOf(ArrayListVsVectorBenchmark.MyState state) {
        state.arrayList.indexOf(new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark03_vectorIndexOf(ArrayListVsVectorBenchmark.MyState state) {
        state.vector.indexOf(new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark04_arrayListGet(ArrayListVsVectorBenchmark.MyState state) {
        state.arrayList.get(state.randomIndex);
    }

    @Benchmark
    public void benchmark04_vectorGet(ArrayListVsVectorBenchmark.MyState state) {
        state.vector.get(state.randomIndex);
    }

    @Benchmark
    public void benchmark05_arrayListRemove(ArrayListVsVectorBenchmark.MyState state) {
        state.arrayList.remove(new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark05_vectorRemove(ArrayListVsVectorBenchmark.MyState state) {
        state.vector.remove(new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark06_arrayListAdd(ArrayListVsVectorBenchmark.MyState state) {
        // 避免添加太多的对象，造成内存溢出
        final int size = state.arrayList.size();
        if (size > state.iterations * 2) {
            state.arrayList.clear();
        }
        state.arrayList.add(new Employee(state.randomIndex, "John"));
    }

    @Benchmark
    public void benchmark06_vectorAdd(ArrayListVsVectorBenchmark.MyState state) {
        // 避免添加太多的对象，造成内存溢出
        final int size = state.vector.size();
        if (size > state.iterations * 2) {
            state.vector.clear();
        }
        state.vector.add(new Employee(state.randomIndex, "John"));
    }

}
