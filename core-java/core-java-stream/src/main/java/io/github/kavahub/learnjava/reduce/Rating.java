package io.github.kavahub.learnjava.reduce;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rating {
    double points;
    List<Review> reviews = new ArrayList<>();

    public Rating() {}

    public void add(Review review) {
        reviews.add(review);
        computeRating();
    }

    /**
     * 计算平均
     * @return
     */
    private double computeRating() {
        double totalPoints = reviews.stream().map(Review::getPoints).reduce(0, Integer::sum);
        this.points = totalPoints / reviews.size();
        return this.points;
    }

    public static Rating average(Rating r1, Rating r2) {
        Rating combined = new Rating();
        combined.reviews = new ArrayList<>(r1.reviews);
        combined.reviews.addAll(r2.reviews);
        combined.computeRating();
        return combined;
    } 
}
