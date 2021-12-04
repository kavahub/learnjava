package io.github.kavahub.learnjava;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 股票实体
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Data
public class Stock {
    private Integer id;
    private String name;
    private BigDecimal price;
    LocalDateTime dateTime;

    public Stock(Integer id, String name, BigDecimal price, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.dateTime = dateTime;
    }
}
