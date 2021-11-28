package io.github.kavahub.learnjava;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 数组的合并及分割
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ArraysJoinAndSplitTest {
    private final String[] sauces = { "Marinara", "Olive Oil" };
    private final String[] cheeses = { "Mozzarella", "Feta", "Parmesan" };
    private final String[] vegetables = { "Olives", "Spinach", "Green Peppers" };

    private final String[] customers = { "Jay", "Harry", "Ronnie", "Gary", "Ross" };

    @Test
    public void givenThreeStringArrays_whenJoiningIntoOneStringArray_shouldSucceed() {
        String[] toppings = new String[sauces.length + cheeses.length + vegetables.length];

        System.arraycopy(sauces, 0, toppings, 0, sauces.length);
        int AddedSoFar = sauces.length;

        System.arraycopy(cheeses, 0, toppings, AddedSoFar, cheeses.length);
        AddedSoFar += cheeses.length;

        System.arraycopy(vegetables, 0, toppings, AddedSoFar, vegetables.length);

        Assertions.assertArrayEquals(toppings, new String[] { "Marinara", "Olive Oil", "Mozzarella", "Feta", "Parmesan", "Olives", "Spinach", "Green Peppers" });
    }

    @Test
    public void givenOneStringArray_whenSplittingInHalfTwoStringArrays_shouldSucceed() {
        int ordersHalved = (customers.length / 2) + (customers.length % 2);

        String[] driverOne = Arrays.copyOf(customers, ordersHalved);
        String[] driverTwo = Arrays.copyOfRange(customers, ordersHalved, customers.length);

        Assertions.assertArrayEquals(driverOne, new String[] { "Jay", "Harry", "Ronnie" });
        Assertions.assertArrayEquals(driverTwo, new String[] { "Gary", "Ross" });
    }    
}
