package io.github.kavahub.learnjava;

import lombok.Data;


@Data
public class Review {
    int points;
    String review;

    public Review(int points, String review) {
        this.points = points;
        this.review = review;
    }
        
}
