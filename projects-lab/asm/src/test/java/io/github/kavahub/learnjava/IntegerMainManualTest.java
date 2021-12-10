package io.github.kavahub.learnjava;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.integer.Main;

/**
 * <p>
 * 使用操作方法：
 * 
 * <p>
 * 修改pom.xml文件，启用 {@code Premain}，修改效果：
 * 
 * <pre>
 * ...
 *      <manifestEntries>
 *           <Premain-Class>
 *               io.github.kavahub.learnjava.integer.PrmainAgent
 *          </Premain-Class>
 *          ...
 *       </manifestEntries>
 * ...
 * </pre>
 *  
 * 使用 Maven 命令打包：mvn clean install , 然后可以运行测试
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class IntegerMainManualTest {
    @Test
    public void start() throws IOException, InterruptedException {
        Process process = Main.start();
        while(process.isAlive()) {}
        System.out.println(" Process end ");
    }
}
