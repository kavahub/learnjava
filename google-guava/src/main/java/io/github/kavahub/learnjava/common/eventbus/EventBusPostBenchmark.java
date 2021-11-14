package io.github.kavahub.learnjava.common.eventbus;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

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
 * @formatter:off
 * Benchmark                    Mode  Cnt      Score     Error   Units
 * EventBusPostBenchmark.post  thrpt   10  13599.140 ± 424.553  ops/ms
 * @formatter:on
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
@State(Scope.Thread)
public class EventBusPostBenchmark {
    private EventBus eventBus;

    public EventBusPostBenchmark() {
        eventBus = new EventBus();
        eventBus.register(new Object() {
            @Subscribe
            public void handler(Integer event) {

            }
        });
    }

    public static void main(String[] args) throws Exception {
        ChainedOptionsBuilder opts = new OptionsBuilder().include(EventBusPostBenchmark.class.getSimpleName());

        for (Integer i : ImmutableList.of(1, 2, 8, 32)) {
            new Runner(opts.threads(i).build()).run();
        }
    }

    @Benchmark
    public void post() {
        eventBus.post(ThreadLocalRandom.current().nextInt(100));
    }
}
