package io.github.kavahub.learnjava;

/**
 * 程序入口
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class TomcatApplication {

  public static void main(String[] args) throws Exception {
    TomcatServer server = new TomcatServer();
    server.run(args);
  }

}
