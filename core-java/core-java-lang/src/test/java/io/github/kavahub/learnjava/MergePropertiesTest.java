package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * {@link Properties} 合并
 */
public class MergePropertiesTest {
    @Test
    public void givenTwoProperties_whenMergedUsingIteration_thenAllPropertiesInResult() {
        Properties globalProperties = mergePropertiesByIteratingKeySet(propertiesA(), propertiesB());

        testMergedProperties(globalProperties);
    }

    @Test
    public void givenTwoProperties_whenMergedUsingPutAll_thenAllPropertiesInResult() {
        Properties globalProperties = mergePropertiesByUsingPutAll(propertiesA(), propertiesB());

        testMergedProperties(globalProperties);
    }

    @Test
    public void givenTwoProperties_whenMergedUsingStreamAPI_thenAllPropertiesInResult() {
        Properties globalProperties = mergePropertiesByUsingStreamApi(propertiesB(), propertiesA());

        testMergedProperties(globalProperties);
    }

    private Properties mergePropertiesByIteratingKeySet(Properties... properties) {
        Properties mergedProperties = new Properties();
        for (Properties property : properties) {
            Set<String> propertyNames = property.stringPropertyNames();
            for (String name : propertyNames) {
                String propertyValue = property.getProperty(name);
                mergedProperties.setProperty(name, propertyValue);
            }
        }
        return mergedProperties;
    }

    private Properties mergePropertiesByUsingPutAll(Properties... properties) {
        Properties mergedProperties = new Properties();
        for (Properties property : properties) {
            mergedProperties.putAll(property);
        }
        return mergedProperties;
    }

    private Properties mergePropertiesByUsingStreamApi(Properties... properties) {
        return Stream.of(properties)
            .collect(Properties::new, Map::putAll, Map::putAll);
    }

    private Properties propertiesA() {
        Properties properties = new Properties();
        properties.setProperty("application.name", "my-app");
        properties.setProperty("application.version", "1.0");
        return properties;
    }

    private Properties propertiesB() {
        Properties properties = new Properties();
        properties.setProperty("property-1", "sample property");
        properties.setProperty("property-2", "another sample property");
        return properties;
    }

    private void testMergedProperties(Properties globalProperties) {
        assertThat(globalProperties.size()).isEqualTo(4);
        assertEquals(globalProperties.getProperty("application.name"), "my-app", "Property should be");
        assertEquals(globalProperties.getProperty("application.version"), "1.0", "Property should be");
        assertEquals(globalProperties.getProperty("property-1"), "sample property", "Property should be");
        assertEquals(globalProperties.getProperty("property-2"), "another sample property", "Property should be");
    }    
}
