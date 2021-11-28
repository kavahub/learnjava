package io.github.kavahub.learnjava;

import java.util.concurrent.TimeUnit;

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
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * synchronized 块与方法的性能测试: 方法性能好一点
 * 
 * <p>
 * 测试结果如下：
 * 
 * <p>
 * 1线程测试
 * <pre>
 * Benchmark                                 Mode  Cnt  Score   Error  Units
 * SynchronizedBenchmark.synchronizedBlock   avgt   10  2.373 ± 0.265  ns/op
 * SynchronizedBenchmark.synchronizedMethod  avgt   10  2.066 ± 0.215  ns/op
 * </pre>
 * 
 * <p>
 * 16线程测试
 * <pre>
 * Benchmark                                 Mode  Cnt   Score   Error  Units
 * SynchronizedBenchmark.synchronizedBlock   avgt   10  16.275 ± 0.552  ns/op
 * SynchronizedBenchmark.synchronizedMethod  avgt   10  14.781 ± 1.419  ns/op
 * </pre>
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
@State(Scope.Thread)
public class SynchronizedBenchmark {
    private SynchronizedObject object = new SynchronizedObject();

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder().include(SynchronizedBenchmark.class.getSimpleName())
                .threads(1)
                .shouldFailOnError(true).build();

        new Runner(opts).run();
    }

    @Benchmark
    public void synchronizedBlock() {
        object.synchronizedBlock();
    }

    @Benchmark
    public void synchronizedMethod() {
        object.synchronizedMethod();
    }

    private class SynchronizedObject {
        private Object LOCK = new Object();

        public synchronized void synchronizedMethod() {

        }

        public void synchronizedBlock() {
            synchronized (LOCK) {

            }
        }

    }
}
