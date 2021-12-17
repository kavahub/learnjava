package io.github.kavahub.learnjava.kafka.exactlyonce;

import lombok.Getter;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
@Getter
public class Tuple {
    private String key;
    private Integer value;

    private Tuple(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public static Tuple of(String key, Integer value) {
        return new Tuple(key, value);
    }
}
