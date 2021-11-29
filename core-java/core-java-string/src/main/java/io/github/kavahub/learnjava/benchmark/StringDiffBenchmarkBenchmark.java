package io.github.kavahub.learnjava.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
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

/**
 * 
 * 比较两个字符串的不同
 * 
 * <p>
 * 测试结果:
 * 
 * <pre>
 * Benchmark                                    Mode  Cnt  Score    Error   Units
 * StringDiffBenchmarkBenchmark.diffMatchPatch  thrpt   10  0.009 ±  0.001  ops/ms
 * StringDiffBenchmarkBenchmark.stringUtils     thrpt   10  4.323 ±  0.083  ops/ms
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
public class StringDiffBenchmarkBenchmark {
    private DiffMatchPatch diffMatchPatch = new DiffMatchPatch();

    private List<String> inputs = randomizeInputs(10000);

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder().include(StringDiffBenchmarkBenchmark.class.getSimpleName())
                .build();

        new Runner(opts).run();
    }

    @Benchmark
    public int diffMatchPatch() {
        for (int i = 0; i < inputs.size() - 1; i++) {
            diffMatchPatch.diffMain(inputs.get(i), inputs.get(i + 1), false);
        }
        return inputs.size();
    }

    @Benchmark
    public int stringUtils() {
        for (int i = 0; i < inputs.size() - 1; i++) {
            StringUtils.difference(inputs.get(i), inputs.get(i + 1));
        }
        return inputs.size();
    }

    /**
     * Creates a list of a given size, containing 30 character long strings,
     * each starting with a static prefix of 10 characters and followed by
     * a random 20 character suffix
     *
     * @return a {@link List} of randomised strings
     */
    private List<String> randomizeInputs(int size) {
        String staticPart = "ABCDEF1234";
        List<String> inputs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            inputs.add(staticPart + RandomStringUtils.randomAlphabetic(20));
        }
        return inputs;
    }
}
