package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * 父泛型类型
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SuperTypeTest {
    @Test
    public void givenGenericToken_whenUsingSuperTypeToken_thenPreservesTheTypeInfo() {
        TypeReference<Map<String, Integer>> token = new TypeReference<Map<String, Integer>>() {};
        Type type = token.getType();

        assertEquals("java.util.Map<java.lang.String, java.lang.Integer>", type.getTypeName());

        Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
        assertEquals("java.lang.String", typeArguments[0].getTypeName());
        assertEquals("java.lang.Integer", typeArguments[1].getTypeName());
    }
    
    public static abstract class TypeReference<T> {

        private final Type type;
    
        public TypeReference() {
            Type superclass = getClass().getGenericSuperclass();
            type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        }
    
        public Type getType() {
            return type;
        }
    }
    
}
