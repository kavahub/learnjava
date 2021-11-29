package io.github.kavahub.learnjava.format;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.ibm.icu.text.MessageFormat;

/**
 * 
 * ICU格式化
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ICUFormat {
    public static String getLabel(Locale locale, Object[] data) {
        // 读取资源文件
        ResourceBundle bundle = ResourceBundle.getBundle("formats", locale);
        String format = bundle.getString("label-icu");
        MessageFormat formatter = new MessageFormat(format, locale);
        return formatter.format(data);
    }

    public static void run(List<Locale> locales) {
        System.out.println("ICU formatter");
        locales.forEach(locale -> System.out.println(getLabel(locale, new Object[] { "Alice", "female", 0 })));
        locales.forEach(locale -> System.out.println(getLabel(locale, new Object[] { "Alice", "female", 1 })));
        locales.forEach(locale -> System.out.println(getLabel(locale, new Object[] { "Alice", "female", 2 })));
        locales.forEach(locale -> System.out.println(getLabel(locale, new Object[] { "Alice", "female", 3 })));
        locales.forEach(locale -> System.out.println(getLabel(locale, new Object[] { "Bob", "male", 0 })));
        locales.forEach(locale -> System.out.println(getLabel(locale, new Object[] { "Bob", "male", 1 })));
        locales.forEach(locale -> System.out.println(getLabel(locale, new Object[] { "Bob", "male", 2 })));
        locales.forEach(locale -> System.out.println(getLabel(locale, new Object[] { "Bob", "male", 3 })));
    }    
}
