package io.github.kavahub.learnjava;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class TomcatApplication {

    public static void main(String[] args) throws Exception {
		Server app = new TomcatServer();
		app.run(args);
    }
}
