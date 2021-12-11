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
    public static Process start() throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = Main.class.getCanonicalName();
    
        Path javaagent = Paths.get("target", "elapse-of-time.jar");
        // 注意：javaagent要放在前面
        ProcessBuilder builder = new ProcessBuilder(javaBin, "-javaagent:" + javaagent.toAbsolutePath().toString(), "-cp",
            classpath, className);
    
        builder.inheritIO();
        return builder.start();
      }
}
