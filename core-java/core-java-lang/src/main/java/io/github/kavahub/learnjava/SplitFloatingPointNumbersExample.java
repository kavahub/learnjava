package io.github.kavahub.learnjava;

import java.math.BigDecimal;

/**
 * 浮点数分割：整数和小数
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SplitFloatingPointNumbersExample {
    public static void main(String[] args) {
        
        double doubleNumber = 24.04d;
        splitUsingFloatingTypes(doubleNumber); 
        splitUsingString(doubleNumber);
        splitUsingBigDecimal(doubleNumber);
    }

    private static void splitUsingFloatingTypes(double doubleNumber) {
        System.out.println("Using Floating Point Arithmetics:");
        int intPart = (int) doubleNumber;
        System.out.println("Double Number: "+doubleNumber);
        System.out.println("Integer Part: "+ intPart);
        System.out.println("Decimal Part: "+ (doubleNumber - intPart));
    }

    private static void splitUsingString(double doubleNumber) {
        System.out.println("Using String Operations:");
        String doubleAsString = String.valueOf(doubleNumber);
        int indexOfDecimal = doubleAsString.indexOf(".");
        System.out.println("Double Number: "+doubleNumber);
        System.out.println("Integer Part: "+ doubleAsString.substring(0, indexOfDecimal));
        System.out.println("Decimal Part: "+ doubleAsString.substring(indexOfDecimal));
    }

    private static void splitUsingBigDecimal(double doubleNumber) {
        System.out.println("Using BigDecimal Operations:");
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(doubleNumber));
        int intValue = bigDecimal.intValue();
        System.out.println("Double Number: "+bigDecimal.toPlainString());
        System.out.println("Integer Part: "+intValue);
        System.out.println("Decimal Part: "+bigDecimal.subtract(new BigDecimal(intValue)).toPlainString());
    }  
}
