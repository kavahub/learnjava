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

import io.github.kavahub.learnjava.util.ReverseString;

/**
 * 
 * {@link ReverseString} 字符串反向性能测试
 * 
 * <p>
 * 测试结果:
 * 
 * <pre>
 * Benchmark                                                        Mode  Cnt      Score      Error   Units
 * ReverseStringBenchmark.reverse                                   thrpt   10   1992.504 ±  176.539  ops/ms
 * ReverseStringBenchmark.reverseTheOrderOfWords                    thrpt   10   2826.956 ±   96.990  ops/ms
 * ReverseStringBenchmark.reverseTheOrderOfWordsUsingApacheCommons  thrpt   10   2230.637 ±   91.337  ops/ms
 * ReverseStringBenchmark.reverseUsingApacheCommons                 thrpt   10  27981.350 ±  975.124  ops/ms
 * ReverseStringBenchmark.reverseUsingStringBuilder                 thrpt   10  37873.653 ± 2624.676  ops/ms
 * </pre>
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class ReverseStringBenchmark {
    private static final String STRING = "abcdefghijklmnopqrstuvwxyz";
    private static final String SENTENCE = "The quick brown fox jumps over the lazy dog";

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder().include(ReverseStringBenchmark.class.getSimpleName())
                .build();

        new Runner(opts).run();
    }

    @Benchmark
    public String reverse() {
        return ReverseString.reverse(STRING);
    }

    @Benchmark
    public String reverseUsingStringBuilder() {
        return ReverseString.reverseUsingStringBuilder(STRING);
    }

    @Benchmark
    public String reverseUsingApacheCommons() {
        return ReverseString.reverseUsingApacheCommons(STRING);
    }

    @Benchmark
    public String reverseTheOrderOfWords() {
        return ReverseString.reverseTheOrderOfWords(SENTENCE);
    }

    @Benchmark
    public String reverseTheOrderOfWordsUsingApacheCommons() {
        return ReverseString.reverseTheOrderOfWordsUsingApacheCommons(SENTENCE);
    }
}
