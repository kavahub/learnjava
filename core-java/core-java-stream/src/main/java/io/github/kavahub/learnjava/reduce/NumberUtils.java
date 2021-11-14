package io.github.kavahub.learnjava.reduce;

import java.util.List;
import java.util.function.BiFunction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberUtils {
    // 函数执行的功能相同，实现方式不同

    public static int divideListElements(List<Integer> values, Integer divider) {
        return values.stream()
                .reduce(0, (a, b) -> {
                    try {
                        return a / divider + b / divider;
                    } catch (ArithmeticException e) {
                        log.info("Arithmetic Exception: Division by Zero");
                    }
                    return 0;
                });
    }
    
    public static int divideListElementsWithExtractedTryCatchBlock(List<Integer> values, int divider) {
        return values.stream().reduce(0, (a, b) -> divide(a, divider) + divide(b, divider));
    }
    
    public static int divideListElementsWithApplyFunctionMethod(List<Integer> values, int divider) {
        BiFunction<Integer, Integer, Integer> division = (a, b) -> a / b;
        return values.stream().reduce(0, (a, b) -> applyFunction(division, a, divider) + applyFunction(division, b, divider));
    }
    
    private static int divide(int value, int factor) {
        int result = 0;
        try {
            result = value / factor;
        } catch (ArithmeticException e) {
            log.info("Arithmetic Exception: Division by Zero");
        }
        return result;
    }
    
    private static int applyFunction(BiFunction<Integer, Integer, Integer> function, int a, int b) {
        try {
            return function.apply(a, b);
        }
        catch(Exception e) {
            log.info("Exception occurred!");
        }
        return 0;
    }    
}
