package io.github.kavahub.learnjava;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link LocalDate} 适配器，使用 {@code DateTimeFormatter#ISO_DATE} 格式转换
 * 
 * <p>
 * jax-ws 不支持 {@code LocalDate}
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String dateString) throws Exception {
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return DateTimeFormatter.ISO_DATE.format(localDate);
    }
    
}
