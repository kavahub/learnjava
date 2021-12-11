package io.github.kavahub.learnjava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

/**
 * 程序入口
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        log.info(">>> Main is running -> {}", Main.class.getName());

        TargetClass target = new TargetClass();
        target.method1();
        target.method2();
    }

    /**
     * 命令行运行，测试使用
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static ProcessBuilder build() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        addJavaBin(builder);
        // 注意：javaagent要放在前面
        addJavaAgent(builder);
        addClasspath(builder);
        addClassMain(builder);
    
        builder.inheritIO();
        return builder;
      }

    private static void addClassMain(ProcessBuilder builder) {
        String className = Main.class.getCanonicalName();
        builder.command().add(className);
    }

    private static void addClasspath(ProcessBuilder builder) {
        String classpath = System.getProperty("java.class.path");
        builder.command().add("-cp");
        builder.command().add(classpath);
    }

    private static void addJavaAgent(ProcessBuilder builder) {
        Path javaagent = Paths.get("target", "elapse-of-time.jar");
        builder.command().add("-javaagent:" + javaagent.toAbsolutePath().toString());
    }

    private static void addJavaBin(ProcessBuilder builder) {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        builder.command().add(javaBin);
    }
}
