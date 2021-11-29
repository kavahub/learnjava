package io.github.kavahub.learnjava;

import lombok.Data;


/**
 * 
 * （辅助类）
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Data
public class Review {
    int points;
    String review;

    public Review(int points, String review) {
        this.points = points;
        this.review = review;
    }
        
}
