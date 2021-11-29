package io.github.kavahub.learnjava;

import java.security.SecureRandom;

/**
 * {@link SecureRandom} 在java各种组件中使用广泛，可以可靠的产生随机数。但在大量产生随机数的场景下，
 * 性能会较低。这时可以使用 "-Djava.security.egd=file:/dev/./urandom" 加快随机数产生过程
 *
 * @author PinWei Wan
 * @since 1.0.0
 * 
 * @see <a href="https://www.it610.com/article/1281618446068498432.htm">java.security.egd 作用</a>
 */
public class JavaSecurityEgdExample {
    public static final double NANOSECS = 1000000000.0;
    public static final String JAVA_SECURITY_EGD = "java.security.egd";

    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        long start = System.nanoTime();
        byte[] randomBytes = new byte[256];
        secureRandom.nextBytes(randomBytes);
        double duration = (System.nanoTime() - start) / NANOSECS;

        String message = String.format("java.security.egd=%s took %.3f seconds and used the %s algorithm",
                System.getProperty(JAVA_SECURITY_EGD), duration, secureRandom.getAlgorithm());
        System.out.println(message);
    }
}
