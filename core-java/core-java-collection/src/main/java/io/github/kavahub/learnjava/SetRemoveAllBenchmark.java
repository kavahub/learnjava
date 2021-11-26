package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
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
 * Benchmark                                                                                                              Mode  Cnt     Score     Error  Units       
 * SetRemoveAllBenchmark.givenSizeOfHashsetGreaterThanSizeOfCollection_WhenRemoveAllFromHashSet_ThenGoodPerformance      thrpt   10  1273.916 ±  43.535  ops/s       
 * SetRemoveAllBenchmark.givenSizeOfHashsetSmallerThanSizeOfAnotherHashSet_WhenRemoveAllFromHashSet_ThenGoodPerformance  thrpt   10   656.568 ± 112.353  ops/s       
 * SetRemoveAllBenchmark.givenSizeOfHashsetSmallerThanSizeOfCollection_WhenRemoveAllFromHashSet_ThenBadPerformance       thrpt   10     0.108 ±   0.015  ops/s       
 * </pre>
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class SetRemoveAllBenchmark {

    @State(Scope.Thread)
    public static class MyState {
        private Set<Employee> employeeSet1 = new HashSet<>();
        private List<Employee> employeeList1 = new ArrayList<>();
        private Set<Employee> employeeSet2 = new HashSet<>();
        private List<Employee> employeeList2 = new ArrayList<>();
        private Set<Employee> employeeSet3 = new HashSet<>();
        private Set<Employee> employeeSet4 = new HashSet<>();

        private int set1Size = 60000;
        private int list1Size = 50000;
        private int set2Size = 50000;
        private int list2Size = 60000;
        private int set3Size = 50000;
        private int set4Size = 60000;

        @Setup(Level.Trial)
        public void setUp() {

            for (int i = 0; i < set1Size; i++) {
                employeeSet1.add(new Employee(i, RandomStringUtils.random(7, true, false)));
            }

            for (int i = 0; i < list1Size; i++) {
                employeeList1.add(new Employee(i, RandomStringUtils.random(7, true, false)));
            }

            for (int i = 0; i < set2Size; i++) {
                employeeSet2.add(new Employee(i, RandomStringUtils.random(7, true, false)));
            }

            for (int i = 0; i < list2Size; i++) {
                employeeList2.add(new Employee(i, RandomStringUtils.random(7, true, false)));
            }
            
            for (int i = 0; i < set3Size; i++) {
                employeeSet3.add(new Employee(i, RandomStringUtils.random(7, true, false)));
            }

            for (int i = 0; i < set4Size; i++) {
                employeeSet4.add(new Employee(i, RandomStringUtils.random(7, true, false)));
            }

        }

    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(SetRemoveAllBenchmark.class.getSimpleName())
            .threads(1)
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .jvmArgs("-server")
            .build();
        new Runner(options).run();
    }

    @Benchmark
    public boolean givenSizeOfHashsetGreaterThanSizeOfCollection_WhenRemoveAllFromHashSet_ThenGoodPerformance(MyState state) {
        return state.employeeSet1.removeAll(state.employeeList1);
    }

    @Benchmark
    public boolean givenSizeOfHashsetSmallerThanSizeOfCollection_WhenRemoveAllFromHashSet_ThenBadPerformance(MyState state) {
        return state.employeeSet2.removeAll(state.employeeList2);
    }
    
    @Benchmark
    public boolean givenSizeOfHashsetSmallerThanSizeOfAnotherHashSet_WhenRemoveAllFromHashSet_ThenGoodPerformance(MyState state) {
        return state.employeeSet3.removeAll(state.employeeSet4);
    }


}
