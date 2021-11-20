package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import lombok.Data;

/**
 * 多个返回值。
 */
public class MultipleReturnValuesTest {
    @Test
    void whenUsingApacheCommonsPair_thenMultipleFieldsAreReturned() {

        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(new Coordinates(1, 1, "home"));
        coordinatesList.add(new Coordinates(2, 2, "school"));
        coordinatesList.add(new Coordinates(3, 3, "hotel"));

        Coordinates target = new Coordinates(5, 5, "gym");

        ImmutablePair<Coordinates, Double> mostDistantPoint = MultipleReturnValuesUsingApacheCommonsPair.getMostDistantPoint(coordinatesList, target);

        assertEquals(1, mostDistantPoint.getLeft().getLongitude());
        assertEquals(1, mostDistantPoint.getLeft().getLatitude());
        assertEquals("home", mostDistantPoint.getLeft().getPlaceName());
        assertEquals(5.66, BigDecimal.valueOf(mostDistantPoint.getRight()).setScale(2, RoundingMode.HALF_UP).doubleValue());

    }

    static class MultipleReturnValuesUsingApacheCommonsPair {

        static ImmutablePair<Coordinates, Double> getMostDistantPoint(
          List<Coordinates> coordinatesList,
          Coordinates target) {
            return coordinatesList.stream()
              .map(coordinates -> ImmutablePair.of(coordinates, coordinates.calculateDistance(target)))
              .max(Comparator.comparingDouble(Pair::getRight))
              .get();
    
        }
    }

    @Test
    void whenUsingApacheCommonsTriple_thenMultipleFieldsAreReturned() {

        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(new Coordinates(1, 1, "home"));
        coordinatesList.add(new Coordinates(2, 2, "school"));
        coordinatesList.add(new Coordinates(3, 3, "hotel"));

        Coordinates target = new Coordinates(5, 5, "gym");

        ImmutableTriple<Double, Double, Double> minAvgMax = MultipleReturnValuesUsingApacheCommonsTriple.getMinAvgMaxTriple(coordinatesList, target);

        assertEquals(2.83, scaleDouble(minAvgMax.left));   //min
        assertEquals(4.24, scaleDouble(minAvgMax.middle)); //avg
        assertEquals(5.66, scaleDouble(minAvgMax.right));  //max
    }
    
    private double scaleDouble(Double d) {
        return BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    
    static class MultipleReturnValuesUsingApacheCommonsTriple {

        static ImmutableTriple<Double, Double, Double> getMinAvgMaxTriple(
          List<Coordinates> coordinatesList,
          Coordinates target) {
    
            List<Double> distanceList = coordinatesList.stream()
              .map(coordinates -> coordinates.calculateDistance(target))
              .collect(Collectors.toList());
            Double minDistance = distanceList.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
            Double avgDistance = distanceList.stream().mapToDouble(Double::doubleValue).average().orElse(0.0D);
            Double maxDistance = distanceList.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
    
            return ImmutableTriple.of(minDistance, avgDistance, maxDistance);
        }
    }

    @Data
    static class Coordinates {

        private double longitude;
        private double latitude;
        private String placeName;
        
        public Coordinates() {}
        
        public Coordinates(double longitude, double latitude, String placeName) {
            this.longitude = longitude;
            this.latitude = latitude;
            this.placeName = placeName;
        }
    
        public double calculateDistance(Coordinates c) {
            
            double s1 = Math.abs(this.longitude - c.longitude);
            double s2 = Math.abs(this.latitude - c.latitude);
            
            return Math.hypot(s1, s2);
        }
    }
}
