package io.github.kavahub.learnjava.math;

import org.paukov.combinatorics3.Generator;

public class CombinationUseCombinatoricsLibExample {
    public static void main(String[] args) {
        Generator.combination(0, 1, 2, 3, 4, 5)
            .simple(3)
            .stream()
            .forEach(System.out::println);
    }   
}