package io.github.kavahub.learnjava.lock;

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
 * @formatter::off
 * 8核心的机器上，8线程是最快的
 * Benchmark                                                         Mode  Cnt    Score     Error   Units
 * ThreadLocalRandomBenchMarker.randomValuesUsingRandom             thrpt   10  449.442 ±  11.584  ops/us
 * ThreadLocalRandomBenchMarker.randomValuesUsingThreadLocalRandom  thrpt   10  806.171 ± 104.025  ops/us
 * @formatter::on
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
@State(Scope.Thread)
public class ThreadLocalRandomBenchMarker {
    private final Random random = new Random();

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
}
