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

/**
 * 
 * 字符串替换性能测试
 * 
 * <p>
 * 测试结果:
 * 
 * <pre>
 * Benchmark                          Mode  Cnt      Score      Error   Units
 * StringReplaceBenchmark.replace     thrpt   10  23091.419 ± 2055.383  ops/ms
 * StringReplaceBenchmark.replaceAll  thrpt   10   2919.702 ±   30.631  ops/ms
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
public class StringReplaceBenchmark {
    final String master = "Hello World Jack!";
    final String target = "Jack";
    final String regexTarget = "(Jack)$";
    final String replacement = "Java";

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder().include(StringReplaceBenchmark.class.getSimpleName())
                .build();

        new Runner(opts).run();
    }

    @Benchmark
    public String replace() {
        return master.replace(target, replacement);
    }

    @Benchmark
    public String replaceAll() {
        return master.replaceAll(regexTarget, replacement);

    }
}
