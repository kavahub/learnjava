package io.github.kavahub.learnjava.benchmark;

import java.util.concurrent.TimeUnit;
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
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


/**
 * 
 * 是否包含字符串 性能测试
 * 
 * <p>
 * 测试结果：
 * 
 * <pre>
 * Benchmark                                      Mode  Cnt     Score     Error   Units
 * ContainsBenchmark.apacheCommonsStringUtils      thrpt   10  1968.972 ±  98.254  ops/ms
 * ContainsBenchmark.lowerCaseContains             thrpt   10  2333.078 ± 170.203  ops/ms
 * ContainsBenchmark.matchesRegularExpression      thrpt   10   234.021 ±  14.882  ops/ms
 * ContainsBenchmark.patternCaseInsensitiveRegexp  thrpt   10  4083.595 ± 243.943  ops/ms
 * ContainsBenchmark.regionMatches                 thrpt   10   282.634 ±  21.358  ops/ms
 * </pre>
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ContainsBenchmark {
    private String src;
    private String dest;
    private Pattern pattern;

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder().include(ContainsBenchmark.class.getSimpleName()).build();

        new Runner(opts).run();
    }

    @Setup
    public void setup() {
        src = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";
        dest = "eiusmod";
        pattern = Pattern.compile(Pattern.quote(dest), Pattern.CASE_INSENSITIVE);
    }

    // toLowerCase() and contains()
    @Benchmark
    public boolean lowerCaseContains() {
        return src.toLowerCase().contains(dest.toLowerCase());
    }

    // matches() with Regular Expressions
    @Benchmark
    public boolean matchesRegularExpression() {
        return src.matches("(?i).*" + dest + ".*");
    }

    public boolean processRegionMatches(String localSrc, String localDest) {
        for (int i = localSrc.length() - localDest.length(); i >= 0; i--)
            if (localSrc.regionMatches(true, i, localDest, 0, localDest.length()))
                return true;
        return false;
    }

    // String regionMatches()
    @Benchmark
    public boolean regionMatches() {
        return processRegionMatches(src, dest);
    }

    // Pattern CASE_INSENSITIVE with regexp
    @Benchmark
    public boolean patternCaseInsensitiveRegexp() {
        return pattern.matcher(src).find();
    }

    // Apache Commons StringUtils containsIgnoreCase
    @Benchmark
    public boolean apacheCommonsStringUtils() {
        return org.apache.commons.lang3.StringUtils.containsIgnoreCase(src, dest);
    }
}
