package io.github.kavahub.learnjava.benchmark;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import io.github.kavahub.learnjava.util.InsertCharacterInString;

/**
 * 字符串中插入子串性能测试。
 * 
 * <p>
 * 在字符串中插入子串有多种方式实现，参考: {@link InsertCharacterInString}
 * 
 * <p>
 * 测试结果:
 * 
 * <pre>
 * Benchmark                                            Mode  Cnt      Score      Error   Units
 * InsertCharInStringBenchmark.insertCharStringBuffer    thrpt   10  56071.058 ±  876.080  ops/ms
 * InsertCharInStringBenchmark.insertCharStringBuilder   thrpt   10  51649.566 ± 7152.233  ops/ms
 * InsertCharInStringBenchmark.insertCharSubstring       thrpt   10  17565.969 ±  265.822  ops/ms
 * InsertCharInStringBenchmark.insertCharUsingCharArray  thrpt   10  56525.726 ± 1839.114  ops/ms
 * </pre>
 * 
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class InsertCharInStringBenchmark {
    final String target = "abcdefghijlmnopqrstuvwxyz";
    final char ch = 'k';

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder().include(InsertCharInStringBenchmark.class.getSimpleName()).build();

        new Runner(opts).run();
    }

    @Benchmark
    public String insertCharSubstring() {
        return InsertCharacterInString.insertCharSubstring(target, ch, 10);
    }

    @Benchmark
    public String insertCharUsingCharArray() {
        return InsertCharacterInString.insertCharUsingCharArray(target, ch, 10);
    }

    @Benchmark
    public String insertCharStringBuilder() {
        return InsertCharacterInString.insertCharStringBuilder(target, ch, 10);
    }

    @Benchmark
    public String insertCharStringBuffer() {
        return InsertCharacterInString.insertCharStringBuffer(target, ch, 10);
    }
}
