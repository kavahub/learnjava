package io.github.kavahub.learnjava;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 *  * 测试操作方法：
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
        ProcessBuilder builder = Main.build();
        Process process = builder.start();

        // 等待程序运行完成
        while(process.isAlive()) {}
    }

    @Test
    public void givenJavassist_whenRunProgream_thenCheckConsole() throws IOException, InterruptedException {
        // 启动程序
        ProcessBuilder builder = Main.build();
        builder.environment().put("class_file_transformer", "io.github.kavahub.learnjava.ClassFileTransformerWithJavassist");
        Process process = builder.start();

        // 等待程序运行完成
        while(process.isAlive()) {}
    }
    
    @Test
    public void givenJavassistStopWatch_whenRunProgream_thenCheckConsole() throws IOException, InterruptedException {
        // 启动程序
        ProcessBuilder builder = Main.build();
        builder.environment().put("class_file_transformer", "io.github.kavahub.learnjava.ClassFileTransformerWithJavassistStopWatch");
        Process process = builder.start();

        // 等待程序运行完成
        while(process.isAlive()) {}
    }

    @Test
    public void givenASM_whenRunProgream_thenCheckConsole() throws IOException, InterruptedException {
        // 启动程序
        ProcessBuilder builder = Main.build();
        builder.environment().put("class_file_transformer", "io.github.kavahub.learnjava.ClassFileTransformerWithASM");
        Process process = builder.start();

        // 等待程序运行完成
        while(process.isAlive()) {}
    }
}
