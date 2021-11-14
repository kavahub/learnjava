package io.github.kavahub.learnjava.resource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

public class ResourceBundleTest {
    @Test
    public void whenGetBundleExampleResourceForLocalePlPl_thenItShouldInheritPropertiesGreetingAndLanguage() {
        Locale plLocale = new Locale("pl", "PL");

        ResourceBundle exampleBundle = ResourceBundle.getBundle("io.github.kavahub.learnjava.resource.ExampleResource", plLocale);

        assertTrue(exampleBundle.keySet()
            .containsAll(Arrays.asList("toUsdRate", "cities", "greeting", "currency", "language")));
        assertEquals(exampleBundle.getString("greeting"), "cześć");
        assertEquals(exampleBundle.getObject("toUsdRate"), new BigDecimal("3.401"));
        assertArrayEquals(exampleBundle.getStringArray("cities"), new String[] { "Warsaw", "Cracow" });
    }

    /**
     * 必须要存在ExampleResource_en.java类。如果没有，在ide中运行测试没有问题，但使用mvn clean install就会测试失败
     * 
     * @see <a href="https://stackoverflow.com/questions/17857712/wrong-java-resource-bundle-loaded">StackOverflow</a>
     * 
     * 
     */
    @Test
    public void whenGetBundleExampleResourceForLocaleUs_thenItShouldContainOnlyGreeting() {
        Locale usLocale = Locale.US;
        

        ResourceBundle exampleBundle = ResourceBundle.getBundle("io.github.kavahub.learnjava.resource.ExampleResource", usLocale);

        System.out.println("======" + exampleBundle.keySet());
        assertFalse(exampleBundle.keySet()
            .containsAll(Arrays.asList("toUsdRate", "cities", "currency", "language")));
        assertTrue(exampleBundle.keySet()
            .containsAll(Arrays.asList("greeting")));          
    }   


}
