package io.github.kavahub.learnjava.util;

import java.util.List;
import java.util.function.BiFunction;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 除法，与集合中的每个数相除
 * 
 * <p>
 * 说明：函数执行的功能相同，实现方式不同
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
public class NumberDivider {
    public int divideListElements(List<Integer> values, Integer divider) {
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
    
    public int divideListElementsWithExtractedTryCatchBlock(List<Integer> values, int divider) {
        return values.stream().reduce(0, (a, b) -> divide(a, divider) + divide(b, divider));
    }
    
    public int divideListElementsWithApplyFunctionMethod(List<Integer> values, int divider) {
        BiFunction<Integer, Integer, Integer> division = (a, b) -> a / b;
        return values.stream().reduce(0, (a, b) -> applyFunction(division, a, divider) + applyFunction(division, b, divider));
    }
    
    private int divide(int value, int factor) {
        int result = 0;
        try {
            result = value / factor;
        } catch (ArithmeticException e) {
            log.info("Arithmetic Exception: Division by Zero");
        }
        return result;
    }
    
    private int applyFunction(BiFunction<Integer, Integer, Integer> function, int a, int b) {
        try {
            return function.apply(a, b);
        }
        catch(Exception e) {
            log.info("Exception occurred!");
        }
        return 0;
    }    
}
