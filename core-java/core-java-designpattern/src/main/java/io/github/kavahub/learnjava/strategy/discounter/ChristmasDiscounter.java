package io.github.kavahub.learnjava.strategy.discounter;

import java.math.BigDecimal;

/**
 * 
 * 圣诞节折扣
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ChristmasDiscounter implements Discounter {

    @Override
    public BigDecimal apply(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(0.9));
    }
    
}
