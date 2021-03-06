package io.github.kavahub.learnjava;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * 测试操作方法：
 * 
 * <p>
 * 使用 Maven 命令打包： mvn clean install，
 * 在 target 目录有 elapse-of-time.jar 文件， 然后运行测试查看控制条输出
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class TargetClassManualTest {

    @Test
    public void givenDefault_whenRunProgream_thenCheckConsole() throws IOException, InterruptedException {
        // 启动程序
        ProcessBuilder builder = MainRunner.INSTANCE.runMain(Main.class).build();
        Process process = builder.start();

        // 等待程序运行完成
        while (process.isAlive()) {
        }
    }

    @Test
    public void givenJavassist_whenRunProgream_thenCheckConsole() throws IOException, InterruptedException {
        // 启动程序
        ProcessBuilder builder = MainRunner.INSTANCE.runMain(Main.class)
                .addEnv("transformer_class", "io.github.kavahub.learnjava.TransformerWithJavassist").build();
        Process process = builder.start();

        // 等待程序运行完成
        while (process.isAlive()) {
        }
    }

    @Test
    public void givenASM_whenRunProgream_thenCheckConsole() throws IOException, InterruptedException {
        // 启动程序
        ProcessBuilder builder = MainRunner.INSTANCE.runMain(Main.class)
                .addEnv("transformer_class", "io.github.kavahub.learnjava.TransformerWithASM").build();
        Process process = builder.start();

        // 等待程序运行完成
        while (process.isAlive()) {
        }
    }

    @Test
    public void givenByteBuddy_whenRunProgream_thenCheckConsole() throws IOException, InterruptedException {
        // 启动程序
        ProcessBuilder builder = MainRunner.INSTANCE.runMain(Main.class)
                .addEnv("transformer_class", "io.github.kavahub.learnjava.TransformerWithByteBuddy").build();
        Process process = builder.start();

        // 等待程序运行完成
        while (process.isAlive()) {
        }
    }
}
