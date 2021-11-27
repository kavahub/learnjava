package io.github.kavahub.learnjava.countryserver;

/**
 * 
 * 币种
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public enum Currency {

    EUR, INR, USD;

    public String value() {
        return name();
    }

    public static Currency fromValue(String v) {
        return valueOf(v);
    }

}
