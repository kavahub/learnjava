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
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * {@link EventBus} 响应性能测试
 * 
 * <p>
 * 性能测试如下：
 * 
 * <pre>
 * Benchmark                           Mode  Cnt     Score     Error   Units
 * EventBusReponseTimeBenchmark.post  thrpt   10  2579.608 ± 781.800  ops/ms
 * </pre>
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
@State(Scope.Thread)
public class EventBusBenchmark {
    private EventBus eventBus;

    @Setup
    public void setup() {
        eventBus = new EventBus();
        eventBus.register(new Object() {
            @Subscribe
            public void handler(Long event) {
                long cost = System.currentTimeMillis() - event.longValue();
                
                try {
                    TimeUnit.MILLISECONDS.sleep(cost);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws Exception {
        ChainedOptionsBuilder opts = new OptionsBuilder().include(EventBusBenchmark.class.getSimpleName());

        new Runner(opts.threads(8).build()).run();
    }

    @Benchmark
    public void post() {
        eventBus.post(System.currentTimeMillis());
    }
}
