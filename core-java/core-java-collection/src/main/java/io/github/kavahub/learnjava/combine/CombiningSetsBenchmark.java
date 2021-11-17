package io.github.kavahub.learnjava.combine;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

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

// Benchmark                                        Mode  Cnt       Score       Error  Units
// CombiningSetsBenchmark.A_usingNativeJava         avgt   10  480775.747 ± 21469.436  ns/op
// CombiningSetsBenchmark.B_usingJava8ObjectStream  avgt   10  455894.355 ± 24523.459  ns/op
// CombiningSetsBenchmark.C_usingJava8FlatMaps      avgt   10  532660.012 ± 71621.736  ns/op
// CombiningSetsBenchmark.D_usingGuava              avgt   10       4.239 ±     0.510  ns/op
// CombiningSetsBenchmark.E_usingApacheCommons      avgt   10       9.705 ±     0.823  ns/op

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class CombiningSetsBenchmark {
    private static final Set<Integer> first = new HashSet<Integer>();
	private static final Set<Integer> second = new HashSet<Integer>();
    private static final int MAX_SIZE = 10000;

    @Setup
    public void setUp() {
        for (int i = 0; i < MAX_SIZE; i++) {
            first.add(ThreadLocalRandom.current().nextInt(MAX_SIZE));
            second.add(ThreadLocalRandom.current().nextInt(MAX_SIZE));
        }
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(CombiningSetsBenchmark.class.getSimpleName()).shouldFailOnError(true)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public Set<Integer> E_usingApacheCommons() {
        return CombiningSets.usingApacheCommons(first, second);
    }

    @Benchmark
    public Set<Integer> D_usingGuava() {
        return CombiningSets.usingGuava(first, second);
    }

    @Benchmark
    public Set<Integer> C_usingJava8FlatMaps() {
        return CombiningSets.usingJava8FlatMaps(first, second);
    }

    @Benchmark
    public Set<Integer> B_usingJava8ObjectStream() {
        return CombiningSets.usingJava8ObjectStream(first, second);
    }

    @Benchmark
    public Set<Integer> A_usingNativeJava() {
        return CombiningSets.usingNativeJava(first, second);
    }
}
