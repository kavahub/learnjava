package io.github.kavahub.learnjava;

import static org.asciidoctor.Asciidoctor.Factory.create;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;

/**
 * {@link Asciidoctor} 工具
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class Asciidoctors {
    
    private final Asciidoctor asciidoctor;

    public Asciidoctors() {
        asciidoctor = create();
    }

    /**
     * 从字符串生成PDF
     * 
     * @param input
     * @return
     */
    public String generatePDFFromString(final String input) {

        final Options options = Options.builder().inPlace(true)
          .backend("pdf")
          .build();

          return asciidoctor.convert(input, options);
    }

    /**
     * 从字符串生成HTML
     * 
     * @param input
     * @return
     */
    public String generateHTMLFromString(final String input) {
        final Options options = Options.builder().inPlace(true)
        .backend("html")
        .build();
        return asciidoctor.convert(input, options);
    }
}
