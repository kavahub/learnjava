package io.github.kavahub.learnjava.integer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 测试效果
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class Main {
  public static void main(String[] args) throws NoSuchFieldException, SecurityException {
    Main main = new Main();
    main.print();
  }

  private void print() {
    Field[] fields = Integer.class.getDeclaredFields();
    System.out.println(fields.length + " >>> " + Stream.of(fields).map(Field::getName).collect(Collectors.joining(",")));
  }

  public static Process start() throws IOException, InterruptedException {
    String javaHome = System.getProperty("java.home");
    String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
    String classpath = System.getProperty("java.class.path");
    String className = Main.class.getCanonicalName();

    Path javaagent = Paths.get("target", "asm.jar");
    ProcessBuilder builder = new ProcessBuilder(javaBin, "-javaagent:" + javaagent.toAbsolutePath().toString(), "-cp",
        classpath, className);

    System.out.println(builder.command().toString());

    builder.inheritIO();
    return builder.start();
  }
}
