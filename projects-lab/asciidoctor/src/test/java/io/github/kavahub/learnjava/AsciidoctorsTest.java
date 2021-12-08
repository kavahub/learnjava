package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * {@link Asciidoctors} 应用示例
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class AsciidoctorsTest {
    @Test
    public void givenString_whenConvertingHTML() {
        final Asciidoctors asciidoctors = new Asciidoctors();
        assertEquals(asciidoctors.generateHTMLFromString("Hello _Java_!"), "<div class=\"paragraph\">\n<p>Hello <em>Java</em>!</p>\n</div>");
    }

    @Test
    @Disabled("org.asciidoctor.jruby.internal.AsciidoctorCoreException: org.jruby.exceptions.TypeError: (TypeError) cannot convert instance of class org.jruby.gen.RubyObject50 to class java.lang.String")
    public void givenString_whenConvertingPDF() {
        final Asciidoctors asciidoctors = new Asciidoctors();
        System.out.println(asciidoctors.generatePDFFromString("Hello _Java_!"));
    }
}
