package io.github.kavahub.learnjava;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
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
 * 读取文件性能测试
 * 
 * <p>
 * 测试结果：
 * 
 * <pre>
 * Benchmark                                           Mode  Cnt   Score    Error  Units
 * ReadFileBenchmark.readAllLinesUsingApacheCommons   thrpt   10  43.111 ±  2.699  ops/s
 * ReadFileBenchmark.readAllLinesUsingBufferedReader  thrpt   10   0.761 ±  0.130  ops/s
 * ReadFileBenchmark.readAllLinesUsingGoogleGuava     thrpt   10  75.422 ±  6.792  ops/s
 * ReadFileBenchmark.readAllLinesUsingJavaFiles       thrpt   10  85.609 ± 13.245  ops/s
 * </pre>
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1)
@Warmup(iterations = 1)
@Fork(1)
public class ReadFileBenchmark {
    private Path file;

    public static void main(String[] args) throws Exception {

        ChainedOptionsBuilder opts = new OptionsBuilder().include(ReadFileBenchmark.class.getSimpleName());

        new Runner(opts.build()).run();
    }

    @Setup
    public void setup() throws Exception {
        file = getFileURIFromResources("largefile.txt");
    }

    @Benchmark
    public String readAllLinesUsingJavaFiles() throws IOException {
        return Files.readString(file);
    }

    @Benchmark
    public String readAllLinesUsingBufferedReader() throws IOException {
        String readData = "";

        try (FileReader fileReader = new FileReader(file.toFile());
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            while (bufferedReader.ready()) {
                char[] c = new char[8192];
                bufferedReader.read(c);
                readData = readData + new String(c);
            }

        }

        return readData;
    }

    @Benchmark
    public String readAllLinesUsingApacheCommons() throws IOException {
        return FileUtils.readFileToString(file.toFile(), "UTF-8");
    }

    @Benchmark
    public String readAllLinesUsingGoogleGuava() throws IOException {
        return com.google.common.io.Files.asCharSource(file.toFile(), Charset.defaultCharset()).read();
    }

    private Path getFileURIFromResources(String fileName) throws Exception {
        final ClassLoader classLoader = ReadFileBenchmark.class.getClassLoader();
        return Paths.get(classLoader.getResource(fileName).toURI());
    }
}
