package io.github.kavahub.learnjava.publish;

import java.io.File;
import java.io.IOException;

/**
 * 发布服务
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class UseMain {
    public static void main(String args[]) throws InterruptedException {
        PublishUtils.publish();
        System.out.println("Server ready...");
        Thread.sleep(30 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }

    /**
     * 使用命令行启动，方便测试使用
     * 
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static Process start() throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = UseMain.class.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);

        // 必须的，否则测试无响应
        builder.inheritIO();
        return builder.start();
    }
}
