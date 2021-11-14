package io.github.kavahub.learnjava.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DistanceBetweenPointsHelpTest {
    private DistanceBetweenPointsHelper helper = new DistanceBetweenPointsHelper();

    @Test
    public void givenTwoPoints_whenCalculateDistanceByFormula_thenCorrect() {

        double x1 = 3;
        double y1 = 4;
        double x2 = 7;
        double y2 = 1;

        double distance = helper.calculateDistanceBetweenPoints(x1, y1, x2, y2);

        assertEquals(distance, 5, 0.001);

    }

    @Test
    public void givenTwoPoints_whenCalculateDistanceWithHypot_thenCorrect() {

        double x1 = 3;
        double y1 = 4;
        double x2 = 7;
        double y2 = 1;

        double distance = helper.calculateDistanceBetweenPointsWithHypot(x1, y1, x2, y2);

        assertEquals(distance, 5, 0.001);

    } 

    @Test
    public void givenSpecialPoints_whenCalculateDistanceWithHypot() {
        double distance = helper.calculateDistanceBetweenPointsWithHypot(0, 0, 3, 4);
        assertEquals(distance, 5, 0.001);
        distance = helper.calculateDistanceBetweenPointsWithHypot(-3, -4, 3, 4);
        assertEquals(distance, 10, 0.001);
    }

    @Test
    public void givenSpecialPoints_whenCalculateDistanceByFormula() {
        double distance = helper.calculateDistanceBetweenPoints(0, 0, 3, 4);
        assertEquals(distance, 5, 0.001);
        distance = helper.calculateDistanceBetweenPoints(-3, -4, 3, 4);
        assertEquals(distance, 10, 0.001);
    }
}
