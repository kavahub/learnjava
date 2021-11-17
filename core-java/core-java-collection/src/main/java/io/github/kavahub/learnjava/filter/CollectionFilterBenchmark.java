package io.github.kavahub.learnjava.filter;

import java.util.Collection;
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

// Benchmark                                                  Mode  Cnt    Score    Error  Units
// CollectionFilterBenchmark.collectionUtilsCollectionFilter  avgt   10   33.661 ± 29.035  us/op
// CollectionFilterBenchmark.eclipseCollectionFilter          avgt   10  128.552 ±  5.338  us/op
// CollectionFilterBenchmark.guavaCollectionFilter            avgt   10    0.005 ±  0.001  us/op
// CollectionFilterBenchmark.streamsCollectionFilter          avgt   10  101.132 ±  0.568  us/op


@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class CollectionFilterBenchmark {
    private static final Set<Integer> target = new HashSet<Integer>();
    private static final int MAX_SIZE = 10000;

    @Setup
    public void setUp() {
        for (int i = 0; i < MAX_SIZE; i++) {
            target.add(ThreadLocalRandom.current().nextInt(MAX_SIZE));
        }
    }

    
    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(CollectionFilterBenchmark.class.getSimpleName()).shouldFailOnError(true)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public Collection<Integer> collectionUtilsCollectionFilter() {
        return CollectionUtilsCollectionFilter.findEvenNumbers(target);
    }

    @Benchmark
    public Collection<Integer> eclipseCollectionFilter() {
        return EclipseCollectionFilter.findEvenNumbers(target);
    }

    @Benchmark
    public Collection<Integer> guavaCollectionFilter() {
        return GuavaCollectionFilter.findEvenNumbers(target);
    }

    @Benchmark
    public Collection<Integer> streamsCollectionFilter() {
        return StreamsCollectionFilter.findEvenNumbers(target);
    }
}
