package io.github.kavahub.learnjava.dataimport;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * repository
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
public class AverageRepository {
    CompletionStage<Double> save(Double average) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("saving average: " + average);
            return average;
        });
    }
}
