package io.github.kavahub.learnjava.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.github.kavahub.learnjava.math.DistanceBetweenPoints.*;

import org.junit.jupiter.api.Test;

public class DistanceBetweenPointsTest {
    @Test
    public void givenTwoPoints_whenCalculateDistanceByFormula_thenCorrect() {

        double x1 = 3;
        double y1 = 4;
        double x2 = 7;
        double y2 = 1;

        double distance = calculateDistanceBetweenPoints(x1, y1, x2, y2);

        assertEquals(distance, 5, 0.001);

    }

    @Test
    public void givenTwoPoints_whenCalculateDistanceWithHypot_thenCorrect() {

        double x1 = 3;
        double y1 = 4;
        double x2 = 7;
        double y2 = 1;

        double distance = calculateDistanceBetweenPointsWithHypot(x1, y1, x2, y2);

        assertEquals(distance, 5, 0.001);

    } 

    @Test
    public void givenSpecialPoints_whenCalculateDistanceWithHypot() {
        double distance = calculateDistanceBetweenPointsWithHypot(0, 0, 3, 4);
        assertEquals(distance, 5, 0.001);
        distance = calculateDistanceBetweenPointsWithHypot(-3, -4, 3, 4);
        assertEquals(distance, 10, 0.001);
    }

    @Test
    public void givenSpecialPoints_whenCalculateDistanceByFormula() {
        double distance = calculateDistanceBetweenPoints(0, 0, 3, 4);
        assertEquals(distance, 5, 0.001);
        distance = calculateDistanceBetweenPoints(-3, -4, 3, 4);
        assertEquals(distance, 10, 0.001);
    }
}
