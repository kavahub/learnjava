package io.github.kavahub.learnjava.strategy.discounter;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

/**
 * 
 * 折扣
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public interface Discounter extends UnaryOperator<BigDecimal> {

    /**
     * 合并折扣
     * @param after
     * @return
     */
    default Discounter combine(Discounter after) {
        return value -> after.apply(this.apply(value));
    }

    static Discounter christmas() {
        return (amount) -> amount.multiply(BigDecimal.valueOf(0.9));
    }

    static Discounter newYear() {
        return (amount) -> amount.multiply(BigDecimal.valueOf(0.8));
    }

    static Discounter easter() {
        return (amount) -> amount.multiply(BigDecimal.valueOf(0.5));
    }
    
}
