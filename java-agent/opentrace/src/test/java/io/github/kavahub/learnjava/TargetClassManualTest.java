package io.github.kavahub.learnjava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

/**
 * 测试操作方法：
 * 
 * <p>
 * 使用 Maven 命令打包： mvn clean install，
 * 在 target 目录有 opentrace.jar 文件， 然后运行测试查看控制条输出
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class TargetClassManualTest {
    @Test
    public void whenRunProgream_thenCheckConsole() throws IOException, InterruptedException {
        // 启动程序
        Process process = runMain(TargetClass.class);
        
        // 等待程序运行完成
        while(process.isAlive()) {}
    }


    /**
     * 命令行运行，测试使用
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Process runMain(Class<?> mainClass) throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = mainClass.getCanonicalName();
    
        Path javaagent = Paths.get("target", "opentrace.jar");
        // 注意：javaagent要放在前面
        ProcessBuilder builder = new ProcessBuilder(javaBin, "-javaagent:" + javaagent.toAbsolutePath().toString(), "-cp",
            classpath, className);
    
        builder.inheritIO();
        return builder.start();
      }
}
