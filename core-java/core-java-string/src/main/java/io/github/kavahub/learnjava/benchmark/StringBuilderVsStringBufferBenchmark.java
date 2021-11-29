package io.github.kavahub.learnjava.benchmark;

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
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 
 * {@code StringBuilder} 与 {@code StringBuffer}性能测试
 * 
 * <p>
 * StringBuilder 类在 Java 5 中被提出，它和 StringBuffer 之间的最大不同在于 StringBuilder
 * 的方法不是线程安全的（不能同步访问）
 * 
 * <p>
 * 由于 StringBuilder 相较于 StringBuffer 有速度优势，所以多数情况下建议使用 StringBuilder 类
 * 
 * <p>
 * 测试结果:
 * 
 * <pre>
 * Benchmark                                                    Mode  Cnt    Score   Error   Units
 * StringBuilderVsStringBufferBenchmark.benchmarkStringBuffer   thrpt   10   81.992 ± 0.786  ops/ms
 * StringBuilderVsStringBufferBenchmark.benchmarkStringBuilder  thrpt   10  123.965 ± 5.609  ops/ms
 * </pre>
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class StringBuilderVsStringBufferBenchmark {
    public static void main(String[] args) throws RunnerException {

        Options opts = new OptionsBuilder().include(StringBuilderVsStringBufferBenchmark.class.getSimpleName())
                .build();

        new Runner(opts).run();
    }

    @State(Scope.Benchmark)
    public static class MyState {
        int iterations = 1000;
        String initial = "abc";
        String suffix = "def";
    }

    @Benchmark
    public String benchmarkStringBuffer(MyState state) {
        StringBuffer stringBuffer = new StringBuffer(state.initial);
        for (int i = 0; i < state.iterations; i++) {
            stringBuffer.append(state.suffix);
        }
        return stringBuffer.toString();
    }

    @Benchmark
    public String benchmarkStringBuilder(MyState state) {
        StringBuilder stringBuilder = new StringBuilder(state.initial);
        for (int i = 0; i < state.iterations; i++) {
            stringBuilder.append(state.suffix);
        }
        return stringBuilder.toString();
    }
}
