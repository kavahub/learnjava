package io.github.kavahub.learnjava.benchmark;

import java.util.concurrent.TimeUnit;

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

import io.github.kavahub.learnjava.util.Palindrome;

/**
 * 回文搜索性能测试，{@link Palindrome}实现回文搜索
 * 
 * <p>
 * 测试结果:
 * 
 * <pre>
 * Benchmark                                           Mode  Cnt     Score      Error   Units
 * PalindromeBenchmark.isPalindrome                    thrpt   10  5922.102 ±  459.177  ops/ms
 * PalindromeBenchmark.isPalindromeRecursive           thrpt   10  5383.302 ±  383.091  ops/ms
 * PalindromeBenchmark.isPalindromeReverseTheString    thrpt   10  4778.576 ±  270.982  ops/ms
 * PalindromeBenchmark.isPalindromeUsingIntStream      thrpt   10  3789.634 ± 1481.347  ops/ms
 * PalindromeBenchmark.isPalindromeUsingStringBuffer   thrpt   10  2967.986 ± 1672.002  ops/ms
 * PalindromeBenchmark.isPalindromeUsingStringBuilder  thrpt   10  4606.913 ±  396.342  ops/ms
 * </pre>
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class PalindromeBenchmark {
    private final static String TARGET = "Level";

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder().include(PalindromeBenchmark.class.getSimpleName())
                .build();

        new Runner(opts).run();
    }

    @Benchmark
    public boolean isPalindrome() {
        return Palindrome.isPalindrome(TARGET);
    }

    @Benchmark
    public boolean isPalindromeReverseTheString() {
        return Palindrome.isPalindromeReverseTheString(TARGET);
    }

    @Benchmark
    public boolean isPalindromeUsingStringBuilder() {
        return Palindrome.isPalindromeUsingStringBuilder(TARGET);
    }

    @Benchmark
    public boolean isPalindromeUsingStringBuffer() {
        return Palindrome.isPalindromeUsingStringBuffer(TARGET);
    }

    @Benchmark
    public boolean isPalindromeRecursive() {
        return Palindrome.isPalindromeRecursive(TARGET);
    }

    @Benchmark
    public boolean isPalindromeUsingIntStream() {
        return Palindrome.isPalindromeUsingIntStream(TARGET);
    }

}
