package io.github.kavahub.learnjava.kafka.consumer;

import lombok.Data;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
@Data
public class CountryPopulation {
    
    private String country;
    private Integer population;

    public CountryPopulation(String country, Integer population) {
        this.country = country;
        this.population = population;
    }
}
