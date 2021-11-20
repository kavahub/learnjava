package io.github.kavahub.learnjava.math;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * 自Java2以来，Java语言增加了一个关键字strictfp，虽然这个关键字在大多数场合比较少用，但是还是有必要了解一下。
 * 
 * <p>
 * strictfp的意思是FP-strict，也就是说精确浮点的意思。在Java虚拟机进行浮点运算时，如果没有指定strictfp关键字时，Java的编译器以及运
 * 行环境在对浮点运算的表达式是采取一种近似于我行我素的行为来完成这些操作，以致于得到的结果往往无法令你满意。
 * 
 * <p>
 * 而一旦使用了strictfp来声明一个类、接口或者方法时，那么所声明的范围内Java的编译器以及运行环境会完全依照浮点规范IEEE-754来执行。
 * 因此如果你想让你的浮点运算更加精确，而且不会因为不同的硬件平台所执行的结果不一致的话，那就请用关键字strictfp。
 */
public class StrictfpClassTest {
    @Test
    public void whenDoubleValueOperate() {
        double d1 = 23e10;
        double d2 = 98e17;

        double doubleSub = d1 - d2;

        assertThat(doubleSub).isNotEqualTo(9.800000230000001E18);
    }

    @Test
    public void whenMethodOfstrictfpClassInvoked_thenIdenticalResultOnAllPlatforms() {
        ScientificCalculator calculator = new ScientificCalculator();
        double result = calculator.sum(23e10, 98e17);
        assertThat(result).isEqualTo(9.800000230000001E18);
        result = calculator.diff(Double.MAX_VALUE, 1.56);
        assertThat(result).isEqualTo(1.7976931348623157E308);
    }

    public static strictfp class ScientificCalculator {

        public double sum(double value1, double value2) {
            return value1 + value2;
        }

        public double diff(double value1, double value2) {
            return value1 - value2;
        }
    }
}
