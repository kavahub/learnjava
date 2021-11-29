package io.github.kavahub.learnjava.benchmark;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

/**
 * 
 * 删除禁用词 性能测试
 * 
 * <p>测试开始时，读取文件及需要过滤的单词，然后执行过滤
 * 
 * <p>
 * 测试结果:
 * 
 * <pre>
 * Benchmark                                  Mode  Cnt   Score   Error  Units
 * RemovingStopwordsBenchmark.removeAll       avgt   10   5.787 ± 0.276  ms/op
 * RemovingStopwordsBenchmark.removeManually  avgt   10   8.134 ± 0.367  ms/op
 * RemovingStopwordsBenchmark.replaceRegex    avgt   10  27.810 ± 0.453  ms/op
 * </pre>
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class RemovingStopwordsBenchmark {
    private String data;

    private List<String> stopwords;

    private String stopwordsRegex;

    public static void main(String[] args) throws Exception {
        Options opts = new OptionsBuilder().include(RemovingStopwordsBenchmark.class.getSimpleName())
                .build();

        new Runner(opts).run();
    }

    @Setup
    public void setup() throws Exception {
        data = new String(Files.readAllBytes(getFileURIFromResources("shakespeare-hamlet.txt")));
        data = data.toLowerCase();
        stopwords = Files.readAllLines(getFileURIFromResources("english_stopwords.txt"));
        stopwordsRegex = stopwords.stream().collect(Collectors.joining("|", "\\b(", ")\\b\\s?"));
    }

    @Benchmark
    public String removeManually() {
        String[] allWords = data.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String word : allWords) {
            if (!stopwords.contains(word)) {
                builder.append(word);
                builder.append(' ');
            }
        }
        return builder.toString().trim();
    }

    @Benchmark
    public String removeAll() {
        // 数组转换成list
        ArrayList<String> allWords = Stream.of(data.split(" "))
                .collect(Collectors.toCollection(ArrayList<String>::new));
        allWords.removeAll(stopwords);
        return allWords.stream().collect(Collectors.joining(" "));
    }

    @Benchmark
    public String replaceRegex() {
        return data.replaceAll(stopwordsRegex, "");
    }

    private final Path getFileURIFromResources(String fileName) throws Exception {
        final ClassLoader classLoader = getClass().getClassLoader();
        return Paths.get(classLoader.getResource(fileName).toURI());
    }
}
