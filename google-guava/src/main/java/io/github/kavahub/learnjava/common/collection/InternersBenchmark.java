package io.github.kavahub.learnjava.common.collection;

import java.util.concurrent.TimeUnit;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 创建字符串性能测试
 * 
 * <p>
 * 测试结果如下：
 * 
 * <pre>
 * Benchmark                           Mode  Cnt    Score    Error  Units
 * InternersBenchmark.stringIntern    thrpt   10   93.129 ±  6.355  ops/s
 * InternersBenchmark.strongInterner  thrpt   10  182.250 ± 21.891  ops/s
 * InternersBenchmark.weakInterner    thrpt   10  174.989 ± 11.146  ops/s
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
public class InternersBenchmark {
    private final static int loopSize = 10000;

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(InternersBenchmark.class.getSimpleName()).threads(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }

    @Benchmark
    public void weakInterner() {
        Interner<String> interner = Interners.newWeakInterner();
        for (int i = 0; i < loopSize; i++) {
            interner.intern(Double.toHexString(Math.random()));
        }
    }

    @Benchmark
    public void strongInterner() {
        Interner<String> interner = Interners.newStrongInterner();
        for (int i = 0; i < loopSize; i++) {
            interner.intern(Double.toHexString(Math.random()));
        }
    }

    @Benchmark
    public void stringIntern() {
        for (int i = 0; i < loopSize; i++) {
            Double.toHexString(Math.random()).intern();
        }
    }
}
