package io.github.kavahub.learnjava.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * {@link PropertyBuilder} 应用示例
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class PropertyBuilderTest {
    @Test
    public void whenBuildPersonWithBuilder_thenObjectHasPropertyValues() {

        Person person = new PersonBuilder().setAge(25).setName("John").build();

        assertEquals(25, person.getAge());
        assertEquals("John", person.getName());
        assertNull(person.getBirthday());

    }
}
