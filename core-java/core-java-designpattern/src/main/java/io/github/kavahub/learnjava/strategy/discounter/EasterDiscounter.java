package io.github.kavahub.learnjava.strategy.discounter;

import java.math.BigDecimal;

/**
 * 
 * 复活节折扣
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class EasterDiscounter implements Discounter {

    @Override
    public BigDecimal apply(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(0.5));
    }
    
}
