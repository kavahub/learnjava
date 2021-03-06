package io.github.kavahub.learnjava.math;

import lombok.experimental.UtilityClass;

/**
 * 两点之间的距离
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class DistanceBetweenPoints {
    public double calculateDistanceBetweenPoints(
        double x1, 
        double y1, 
        double x2, 
        double y2) {
        
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public double calculateDistanceBetweenPointsWithHypot(
        double x1, 
        double y1, 
        double x2, 
        double y2) {
        
        double ac = Math.abs(y2 - y1);
        double cb = Math.abs(x2 - x1);
        
        return Math.hypot(ac, cb);
    }
}
