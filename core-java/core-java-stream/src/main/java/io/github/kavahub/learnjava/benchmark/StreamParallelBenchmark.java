package io.github.kavahub.learnjava.benchmark;

import java.util.ArrayList;
import java.util.List;
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

import io.github.kavahub.learnjava.User;

/**
 * {@link Stream} 多线程与单线程性能测试
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class StreamParallelBenchmark {
    private final List<User> userList = createUsers();

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder().include(StreamParallelBenchmark.class.getSimpleName())
        .shouldFailOnError(true)
        .threads(8)
        .build();

        new Runner(opts).run();
    }
    
    private List<User> createUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i <= 1000000; i++) {
            users.add(new User("John" + i, i));
        }
        return users;
    }

    @Benchmark
    public Integer executeReduceOnParallelizedStream() {
        return this.userList
                .parallelStream()
                .reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
    }

    @Benchmark
    public Integer executeReduceOnSequentialStream() {
        return this.userList
                .stream()
                .reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
    }
}
