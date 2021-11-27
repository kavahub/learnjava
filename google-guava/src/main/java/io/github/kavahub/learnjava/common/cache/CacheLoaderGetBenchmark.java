package io.github.kavahub.learnjava.common.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

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
 * {@link CacheLoader} 的get方法性能测试
 * 
 * <p>
 * 测试结果如下：
 * 
 * <pre>
 * Benchmark                              Mode  Cnt      Score      Error   Units
 * CacheLoaderGetBenchmark.get           thrpt   10  26418.137 ±  592.573  ops/ms
 * CacheLoaderGetBenchmark.getIfPresent  thrpt   10  24938.504 ± 1337.478  ops/ms
 * CacheLoaderGetBenchmark.getUnchecked  thrpt   10  25008.564 ± 1133.266  ops/ms
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
public class CacheLoaderGetBenchmark {
    private final static int MAX_SIZE = 10000;

    private LoadingCache<Integer, String> cache;

    public CacheLoaderGetBenchmark() {
        CacheLoader<Integer, String> loader = new CacheLoader<Integer, String>() {
            @Override
            public final String load(final Integer key) {
                return key.toString();
            }
        };

        cache = CacheBuilder.newBuilder().build(loader);
        for (int i = 0; i < MAX_SIZE; i++) {
            cache.put(i, String.valueOf(i));
        }
    }

    public static void main(String[] args) throws Exception {
        ChainedOptionsBuilder opts = new OptionsBuilder().include(CacheLoaderGetBenchmark.class.getSimpleName());

        new Runner(opts.threads(1).build()).run();
    }

    // 内部调用getOrLoad(K key)方法，缓存中有对应的值则返回，没有则使用CacheLoader load方法
    @Benchmark
    public String get() throws ExecutionException {
        return cache.get(ThreadLocalRandom.current().nextInt(MAX_SIZE));
    }

    @Benchmark
    public String getUnchecked() {
        return cache.getUnchecked(ThreadLocalRandom.current().nextInt(MAX_SIZE));
    }

    // 缓存中有对应的值则返回，没有则返回NULL
    @Benchmark
    public String getIfPresent() {
        return cache.getIfPresent(ThreadLocalRandom.current().nextInt(MAX_SIZE));
    }
}
