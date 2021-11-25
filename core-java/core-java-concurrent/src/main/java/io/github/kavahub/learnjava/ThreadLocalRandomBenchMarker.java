package io.github.kavahub.learnjava;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.ImmutableList;

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
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 
 * 随机数生成性能测试
 * 
 * <pre>
 * Benchmark                                                         Mode  Cnt    Score    Error   Units
 * ThreadLocalRandomBenchMarker.randomValuesUsingRandom             thrpt   10  429.473 ± 25.148  ops/us
 * ThreadLocalRandomBenchMarker.randomValuesUsingSecureRandom       thrpt   10    2.775 ±  0.160  ops/us
 * ThreadLocalRandomBenchMarker.randomValuesUsingThreadLocalRandom  thrpt   10  765.769 ± 80.361  ops/us 
 * </pre>
 * 
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
@State(Scope.Thread)
public class ThreadLocalRandomBenchMarker {
    private final Random random = new Random();
    private final SecureRandom  secureRandom = new SecureRandom();

    public static void main(String[] args) throws Exception {
        ChainedOptionsBuilder opts = new OptionsBuilder().include(ThreadLocalRandomBenchMarker.class.getSimpleName());

        for (Integer i : ImmutableList.of(1, 2, 8, 32)) {
            new Runner(opts.threads(i).build()).run();
        }
    }

    @Benchmark
    public int randomValuesUsingRandom() {
        return random.nextInt();
    }

    @Benchmark
    public int randomValuesUsingThreadLocalRandom() {
        return ThreadLocalRandom.current().nextInt();
    }

    @Benchmark
    public int randomValuesUsingSecureRandom() {
        return secureRandom.nextInt();
    }
}
