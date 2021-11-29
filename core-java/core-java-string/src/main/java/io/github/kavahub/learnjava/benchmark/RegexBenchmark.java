package io.github.kavahub.learnjava.benchmark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 
 * 正则表达式性能测试，使用预先编译好的正则表达式性能更好
 * 
 * <p>
 * 测试结果:
 * 
 * <pre>
 * Benchmark                                                 Mode  Cnt  Score   Error  Units
 * RegexBenchmark.matcherFromPreCompiledPatternResetMatches  thrpt   10  3.803 ± 0.185  ops/s
 * RegexBenchmark.patternCompileMatcherMatches               thrpt   10  0.780 ± 0.005  ops/s
 * RegexBenchmark.patternMatches                             thrpt   10  0.741 ± 0.082  ops/s
 * RegexBenchmark.preCompiledPatternMatcherMatches           thrpt   10  2.474 ± 0.467  ops/s
 * RegexBenchmark.stringMatchs                               thrpt   10  0.803 ± 0.071  ops/s
 * </pre>
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class RegexBenchmark {
    private static final String PATTERN = "\\d*[02468]";
    private static List<String> values;

    private static Matcher matcherFromPreCompiledPattern;
    private static Pattern preCompiledPattern;

    public static void main(String[] args) throws IOException, RunnerException {
        Options opts = new OptionsBuilder().include(RegexBenchmark.class.getSimpleName()).build();

        new Runner(opts).run();
    }

    @Setup()
    public void setUp() {
        preCompiledPattern = Pattern.compile(PATTERN);
        // 预热
        matcherFromPreCompiledPattern = preCompiledPattern.matcher("");

        values = new ArrayList<>();
        for (int x = 1; x <= 5_000_000; x++) {
            values.add(String.valueOf(x));
        }
    }

    @Benchmark
    public void matcherFromPreCompiledPatternResetMatches(Blackhole bh) {
        // With pre-compiled pattern and reusing the matcher
        // 1 Pattern object created
        // 1 Matcher objects created
        for (String value : values) {
            bh.consume(matcherFromPreCompiledPattern.reset(value).matches());
        }
    }

    @Benchmark
    public void preCompiledPatternMatcherMatches(Blackhole bh) {
        // With pre-compiled pattern
        // 1 Pattern object created
        // 5_000_000 Matcher objects created
        for (String value : values) {
            bh.consume(preCompiledPattern.matcher(value).matches());
        }
    }

    @Benchmark
    public void patternCompileMatcherMatches(Blackhole bh) {
        // Above approach "Pattern.matches(PATTERN, value)" makes this internally
        // 5_000_000 Pattern objects created
        // 5_000_000 Matcher objects created
        for (String value : values) {
            bh.consume(Pattern.compile(PATTERN).matcher(value).matches());
        }
    }

    @Benchmark
    public void patternMatches(Blackhole bh) {
        // Above approach "value.matches(PATTERN)" makes this internally
        // 5_000_000 Pattern objects created
        // 5_000_000 Matcher objects created
        for (String value : values) {
            bh.consume(Pattern.matches(PATTERN, value));
        }
    }

    @Benchmark
    public void stringMatchs(Blackhole bh) {
        // 5_000_000 Pattern objects created
        // 5_000_000 Matcher objects created
        for (String value : values) {
            bh.consume(value.matches(PATTERN));
        }
    }

}
