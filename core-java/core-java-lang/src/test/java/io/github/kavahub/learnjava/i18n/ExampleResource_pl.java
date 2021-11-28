package io.github.kavahub.learnjava.i18n;

import java.util.ListResourceBundle;

/**
 * 
 * 国际化
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ExampleResource_pl extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] { 
            { "greeting", "cześć" }, 
            { "language", "polish" }, 
        };
    }
    
}
