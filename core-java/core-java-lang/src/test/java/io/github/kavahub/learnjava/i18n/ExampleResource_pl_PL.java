package io.github.kavahub.learnjava.i18n;

import java.math.BigDecimal;
import java.util.ListResourceBundle;

/**
 * 
 * 国际化
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ExampleResource_pl_PL extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] { 
            { "currency", "polish zloty" }, 
            { "toUsdRate", new BigDecimal("3.401") },
            { "cities", new String[] { "Warsaw", "Cracow" } } 
        };
    }
    
}
