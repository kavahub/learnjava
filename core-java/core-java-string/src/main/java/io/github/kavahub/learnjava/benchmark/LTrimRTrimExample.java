package io.github.kavahub.learnjava.benchmark;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.google.common.base.CharMatcher;

import org.apache.commons.lang3.StringUtils;
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
 * 
 * @formatter:off
 * Benchmark                                       Mode  Cnt      Score     Error   Units
 * LTrimRTrimExample.apacheCommonsStringUtils     thrpt   10  10482.755 ±  88.442  ops/ms
 * LTrimRTrimExample.guavaCharMatcher             thrpt   10   9661.441 ± 863.516  ops/ms
 * LTrimRTrimExample.patternMatchesLTtrimRTrim    thrpt   10   1514.144 ±  12.534  ops/ms
 * LTrimRTrimExample.replaceAllRegularExpression  thrpt   10   1035.478 ±  48.099  ops/ms
 * LTrimRTrimExample.whileCharacters              thrpt   10   9383.655 ± 498.941  ops/ms
 * @formatter:on
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class LTrimRTrimExample {
    private String src;
    private static String ltrimResult;
    private static String rtrimResult;
    private static Pattern LTRIM = Pattern.compile("^\\s+");
    private static Pattern RTRIM = Pattern.compile("\\s+$");

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder().include(LTrimRTrimExample.class.getSimpleName())
                .build();

        new Runner(opts).run();
    }

    @Setup
    public void setup() {
        src = "       White spaces left and right          ";
        ltrimResult = "White spaces left and right          ";
        rtrimResult = "       White spaces left and right";
    }

    private String whileLtrim(String s) {
        int i = 0;
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            i++;
        }
        return s.substring(i);
    }

    private String whileRtrim(String s) {
        int i = s.length() - 1;
        while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
            i--;
        }
        return s.substring(0, i + 1);
    }

    private boolean checkStrings(String ltrim, String rtrim) {
        boolean result = false;

        if (ltrimResult.equalsIgnoreCase(ltrim) && rtrimResult.equalsIgnoreCase(rtrim))
            result = true;

        return result;
    }

    // Going through the String detecting Whitespaces
    @Benchmark
    public boolean whileCharacters() {
        String ltrim = whileLtrim(src);
        String rtrim = whileRtrim(src);

        return checkStrings(ltrim, rtrim);
    }

    // replaceAll() and Regular Expressions
    @Benchmark
    public boolean replaceAllRegularExpression() {
        String ltrim = src.replaceAll("^\\s+", "");
        String rtrim = src.replaceAll("\\s+$", "");

        return checkStrings(ltrim, rtrim);
    }

    private String patternLtrim(String s) {
        return LTRIM.matcher(s)
            .replaceAll("");
    }

    private String patternRtrim(String s) {
        return RTRIM.matcher(s)
            .replaceAll("");
    }

    // Pattern matches() with replaceAll
    @Benchmark
    public boolean patternMatchesLTtrimRTrim() {
        String ltrim = patternLtrim(src);
        String rtrim = patternRtrim(src);

        return checkStrings(ltrim, rtrim);
    }

    // Guava CharMatcher trimLeadingFrom / trimTrailingFrom
    @Benchmark
    public boolean guavaCharMatcher() {
        String ltrim = CharMatcher.whitespace().trimLeadingFrom(src);
        String rtrim = CharMatcher.whitespace().trimTrailingFrom(src);

        return checkStrings(ltrim, rtrim);
    }

    // Apache Commons StringUtils containsIgnoreCase
    @Benchmark
    public boolean apacheCommonsStringUtils() {
        String ltrim = StringUtils.stripStart(src, null);
        String rtrim = StringUtils.stripEnd(src, null);

        return checkStrings(ltrim, rtrim);
    }    
}
