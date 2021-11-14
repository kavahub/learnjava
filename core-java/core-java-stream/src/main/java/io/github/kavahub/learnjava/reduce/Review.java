package io.github.kavahub.learnjava.reduce;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
    int points;
    String review;

    public Review(int points, String review) {
        this.points = points;
        this.review = review;
    }
        
}
