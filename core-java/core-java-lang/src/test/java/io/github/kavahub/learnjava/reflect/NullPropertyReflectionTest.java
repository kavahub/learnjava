package io.github.kavahub.learnjava.reflect;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.github.kavahub.learnjava.reflect.NullPropertyReflection.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.Data;

/**
 * {@link ModifiNullPropertyReflectioner} 示例
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class NullPropertyReflectionTest {
    @Test
    public void givenCustomer_whenAFieldIsNull_thenFieldNameInResult() throws Exception {
        Customer customer = new Customer(1, "Himanshu", null, null);

        List<String> result = getNullPropertiesList(customer);
        List<String> expectedFieldNames = Arrays.asList("emailId", "phoneNumber");

        assertTrue(result.size() == expectedFieldNames.size());
        assertTrue(result.containsAll(expectedFieldNames));

    }

    @Data
    public static class Customer {

        private Integer id;
        private String name;
        private String emailId;
        private Long phoneNumber;
    
        @Override
        public String toString() {
            return "Customer [id=" + id + ", name=" + name + ", emailId=" + emailId + ", phoneNumber=" +
              phoneNumber + "]";
        }
    
        Customer(Integer id, String name, String emailId, Long phoneNumber) {
            super();
            this.id = id;
            this.name = name;
            this.emailId = emailId;
            this.phoneNumber = phoneNumber;
        }

    }
}
