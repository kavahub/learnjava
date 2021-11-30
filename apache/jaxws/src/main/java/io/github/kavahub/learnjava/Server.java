package io.github.kavahub.learnjava;

import java.io.File;
import java.io.IOException;

import jakarta.xml.ws.Endpoint;

/**
 * 服务启动入口
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class Server {
    public static void main(String args[]) throws InterruptedException {
        StudentService studentService = new StudentServiceImpl();
        String address = "http://localhost:9080/student";
        Endpoint.publish(address, studentService);
        System.out.println("Server ready...");
        Thread.sleep(60 * 1000);
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
        String className = Server.class.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);

        return builder.start();
    }
}
