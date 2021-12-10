package io.github.kavahub.learnjava.elapse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(">>> Main.main called ");
        Thread.sleep((new Random()).nextInt(1000));
    }

    public static Process start() throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = Main.class.getCanonicalName();
    
        Path javaagent = Paths.get("target", "asm.jar");
        // javaagent参数要放到前面，允许多个javaagent参数
        ProcessBuilder builder = new ProcessBuilder(javaBin, "-javaagent:" + javaagent.toAbsolutePath().toString(), "-cp", classpath, className);
    
        System.out.println(builder.command().toString());
    
        builder.inheritIO();
        return builder.start();
      }
}
