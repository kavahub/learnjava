package io.github.kavahub.learnjava.common.eventbus;

import java.util.concurrent.TimeUnit;

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
 * Max:30
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
@State(Scope.Thread)
public class EventBusReponseTimeBenchmark {
    private EventBus eventBus;


    public EventBusReponseTimeBenchmark() {
        eventBus = new EventBus();
        eventBus.register(new Object() {
            private long minTime = Long.MAX_VALUE;
            private long maxTime = Long.MIN_VALUE;

            @Subscribe
            public void handler(Long event) {
                long cost = System.currentTimeMillis() - event.longValue();
                if (cost < minTime) {
                    minTime = cost;
                    System.out.println("Min:" + cost);
                } 
                
                if (cost > maxTime) {
                    maxTime = cost;
                    System.out.println("Max:" + cost);
                }
            }
        });
    }

    public static void main(String[] args) throws Exception {
        ChainedOptionsBuilder opts = new OptionsBuilder().include(EventBusReponseTimeBenchmark.class.getSimpleName());

        new Runner(opts.threads(8).build()).run();
    }

    @Benchmark
    public void post() {
        eventBus.post(System.currentTimeMillis());
    }
}
