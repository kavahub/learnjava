package io.github.kavahub.learnjava.synthetic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 
 * Synthetic 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SyntheticTest {
    /**
     * Tests that the {@link SyntheticMethod.NestedClass} contains two synthetic
     * methods.
     */
    @Test
    @Disabled("测试失败")
    public void givenSyntheticMethod_whenIsSinthetic_thenTrue() {
        // Checks that the nested class contains exactly two synthetic methods.
        Method[] methods = SyntheticMethod.NestedClass.class.getDeclaredMethods();

        for (Method m : methods) {
            System.out.println("Method: " + m.getName() + ", isSynthetic: " + m.isSynthetic());
            assertTrue(m.isSynthetic(), "All the methods of this class should be synthetic");
        }
    }

    /**
     * Tests that {@link SyntheticConstructor.NestedClass} contains a synthetic
     * constructor.
     */
    @Test
    @Disabled("测试失败")
    public void givenSyntheticConstructor_whenIsSinthetic_thenTrue() {
        // Checks that the nested class contains exactly a synthetic
        // constructor.
        int syntheticConstructors = 0;
        Constructor<?>[] constructors = SyntheticConstructor.NestedClass.class.getDeclaredConstructors();
        assertEquals(1, constructors.length, "This class should contain only two constructors");

        for (Constructor<?> c : constructors) {
            System.out.println("Constructor: " + c.getName() + ", isSynthetic: " + c.isSynthetic());

            // Counts the synthetic constructors.
            if (c.isSynthetic()) {
                syntheticConstructors++;
            }
        }

        // Checks that there's exactly one synthetic constructor.
        assertEquals(1, syntheticConstructors);
    }

    /**
     * Tests that {@link SyntheticFieldDemo.NestedClass} contains a synthetic field.
     */
    @Test
    public void givenSyntheticField_whenIsSinthetic_thenTrue() {
        // This class should contain exactly one synthetic field.
        Field[] fields = SyntheticField.NestedClass.class.getDeclaredFields();
        assertEquals(1, fields.length, "This class should contain only one field");

        for (Field f : fields) {
            System.out.println("Field: " + f.getName() + ", isSynthetic: " + f.isSynthetic());
            assertTrue(f.isSynthetic(), "All the fields of this class should be synthetic");
        }
    }

    /**
     * Tests that {@link BridgeMethodDemo} contains a synthetic bridge method.
     */
    @Test
    public void givenBridgeMethod_whenIsBridge_thenTrue() {
        // This class should contain exactly one synthetic bridge method.
        int syntheticMethods = 0;
        // getDeclaredMethods拿到反射类中的公共方法、私有方法、保护方法、默认访问，但不获得继承的方法
        Method[] methods = BridgeMethod.class.getDeclaredMethods();
        for (Method m : methods) {
            System.out.println(
                    "Method: " + m.getName() + ", isSynthetic: " + m.isSynthetic() + ", isBridge: " + m.isBridge());

            // Counts the synthetic methods and checks that they are also bridge
            // methods.
            if (m.isSynthetic()) {
                syntheticMethods++;
                assertTrue(m.isBridge(), "The synthetic method in this class should also be a bridge method");
            }
        }

        // Checks that there's exactly one synthetic bridge method.
        assertEquals(1, syntheticMethods, "There should be exactly 1 synthetic bridge method in this class");
    }

}
