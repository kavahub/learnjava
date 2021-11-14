package io.github.kavahub.learnjava;

/**
 * 常量的最佳实践
 * 
 * @see <a href="https://www.baeldung.com/java-constants-good-practices">Baeldung</a>
 * 
 * 
 */
public final class MathConstants {
    public static final double PI = 3.14159265359;
    static final double GOLDEN_RATIO = 1.6180;
    static final double GRAVITATIONAL_ACCELERATION = 9.8;
    static final double EULERS_NUMBER = 2.7182818284590452353602874713527;

    private static final double DOUBLE_UPPER_LIMIT = 0x1.fffffffffffffP+1023;

    public enum Operation {
        ADD, SUBTRACT, DIVIDE, MULTIPLY
    }

    private MathConstants() {

    }   
    
    public static double operateOnTwoNumbers(double numberOne, double numberTwo, Operation operation) {
        if (numberOne > DOUBLE_UPPER_LIMIT) {
            throw new IllegalArgumentException("'numberOne' is too large");
        }
        if (numberTwo > DOUBLE_UPPER_LIMIT) {
            throw new IllegalArgumentException("'numberTwo' is too large");
        }
        double answer = 0;

        switch (operation) {
        case ADD:
            answer = numberOne + numberTwo;
            break;
        case SUBTRACT:
            answer = numberOne - numberTwo;
            break;
        case DIVIDE:
            answer = numberOne / numberTwo;
            break;
        case MULTIPLY:
            answer = numberOne * numberTwo;
            break;
        }

        return answer;
    }
}
