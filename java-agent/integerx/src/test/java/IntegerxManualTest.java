import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.Main;

/**
 * 测试操作方法：
 * 
 * <p>
 * 使用 Maven 命令打包： mvn clean install，
 * 在 target 目录有 integerx.jar 文件， 然后运行测试查看控制条输出
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class IntegerxManualTest {
    @Test
    public void whenRunProgream_thenCheckConsole() throws IOException, InterruptedException {
        // 启动程序
        Process process = Main.start();
        
        // 等待程序运行完成
        while(process.isAlive()) {}
    }
}
