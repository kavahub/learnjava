package io.github.kavahub.learnjava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * main 运行器
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class MainRunner {
    public final static MainRunner INSTANCE = new MainRunner();

    private List<String> commands = new ArrayList<>();
    private Class<?> mainClass;
    private Map<String, String> environment = new HashMap<>();

    private MainRunner() {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        commands.add(javaBin);

        Path javaagent = Paths.get("target", "elapse-of-time.jar");
        commands.add("-javaagent:" + javaagent.toAbsolutePath().toString());

        String classpath = System.getProperty("java.class.path");
        commands.add("-cp");
        commands.add(classpath);
    }

    public MainRunner runMain(Class<?> mainClass) {
        Objects.requireNonNull(mainClass);
        this.mainClass = mainClass;
        return this;
    }

    public MainRunner loadEnvFromFile(String file) {
        Objects.requireNonNull(file);

        // 读取属性文件到 environment 中
        Path filePath = Paths.get("src", "main", "resources", file);
        Properties properties = new Properties();
        try (FileInputStream is = new FileInputStream(filePath.toFile())) {
            properties.load(is);

            environment = properties.entrySet().stream().collect(
                    Collectors.toMap(
                            e -> String.valueOf(e.getKey()),
                            e -> String.valueOf(e.getValue()),
                            (prev, next) -> next, HashMap::new));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    public MainRunner loadEnvFromMap(Map<String, String> map) {
        Objects.requireNonNull(map);

        environment.clear();
        environment.putAll(map);
        return this;
    }

    public MainRunner addEnv(String key, String value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        environment.put(key, value);
        return this;
    }

    public ProcessBuilder build() {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command().addAll(commands);
        builder.command().add(mainClass.getCanonicalName());
        builder.environment().putAll(environment);
        builder.inheritIO();
        return builder;
    }
}
