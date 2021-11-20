package io.github.kavahub.learnjava.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;

/**
 * 私有属性获取
 */
public class PrivateFieldGetTest {
    @Test
    public void whenGetIntegerFields_thenSuccess() throws Exception {
        Person person = new Person();

        Field ageField = person.getClass()
            .getDeclaredField("age");
        ageField.setAccessible(true);

        byte age = ageField.getByte(person);
        assertEquals(30, age);

        Field uidNumberField = person.getClass()
            .getDeclaredField("uidNumber");
        uidNumberField.setAccessible(true);

        short uidNumber = uidNumberField.getShort(person);
        assertEquals(5555, uidNumber);

        Field pinCodeField = person.getClass()
            .getDeclaredField("pinCode");
        pinCodeField.setAccessible(true);

        int pinCode = pinCodeField.getInt(person);
        assertEquals(452002, pinCode);

        Field contactNumberField = person.getClass()
            .getDeclaredField("contactNumber");
        contactNumberField.setAccessible(true);

        long contactNumber = contactNumberField.getLong(person);
        assertEquals(123456789L, contactNumber);

    }

    @Test
    public void whenDoAutoboxing_thenSuccess() throws Exception {
        Person person = new Person();

        Field pinCodeField = person.getClass()
            .getDeclaredField("pinCode");
        pinCodeField.setAccessible(true);

        Integer pinCode = pinCodeField.getInt(person);
        assertEquals(452002, pinCode);
    }

    @Test
    public void whenDoWidening_thenSuccess() throws Exception {
        Person person = new Person();

        Field pinCodeField = person.getClass()
            .getDeclaredField("pinCode");
        pinCodeField.setAccessible(true);

        Long pinCode = pinCodeField.getLong(person);
        assertEquals(452002L, pinCode);
    }

    @Test
    public void whenGetFloatingTypeFields_thenSuccess() throws Exception {
        Person person = new Person();

        Field heightField = person.getClass()
            .getDeclaredField("height");
        heightField.setAccessible(true);

        float height = heightField.getFloat(person);
        assertEquals(6.1242f, height);

        Field weightField = person.getClass()
            .getDeclaredField("weight");
        weightField.setAccessible(true);

        double weight = weightField.getDouble(person);
        assertEquals(75.2564, weight);
    }

    @Test
    public void whenGetCharacterFields_thenSuccess() throws Exception {
        Person person = new Person();

        Field genderField = person.getClass()
            .getDeclaredField("gender");
        genderField.setAccessible(true);

        char gender = genderField.getChar(person);
        assertEquals('M', gender);
    }

    @Test
    public void whenGetBooleanFields_thenSuccess() throws Exception {
        Person person = new Person();

        Field activeField = person.getClass()
            .getDeclaredField("active");
        activeField.setAccessible(true);

        boolean active = activeField.getBoolean(person);
        assertTrue(active);
    }

    @Test
    public void whenGetObjectFields_thenSuccess() throws Exception {
        Person person = new Person();

        Field nameField = person.getClass()
            .getDeclaredField("name");
        nameField.setAccessible(true);

        String name = (String) nameField.get(person);
        assertEquals("John", name);
    }

    @Test
    public void givenInt_whenGetStringField_thenIllegalArgumentException() throws Exception {
        Person person = new Person();
        Field nameField = person.getClass()
            .getDeclaredField("name");
        nameField.setAccessible(true);

        assertThrows(IllegalArgumentException.class, () -> nameField.getInt(person));
    }

    @Test
    public void givenInt_whenGetLongField_thenIllegalArgumentException() throws Exception {
        Person person = new Person();
        Field contactNumberField = person.getClass()
            .getDeclaredField("contactNumber");
        contactNumberField.setAccessible(true);

        assertThrows(IllegalArgumentException.class, () -> contactNumberField.getInt(person));
    }

    @Test
    public void whenFieldNotSetAccessible_thenIllegalAccessException() throws Exception {
        Person person = new Person();
        Field nameField = person.getClass()
            .getDeclaredField("name");

        Object name = nameField.get(person);
        assertEquals("John", name.toString());

        // 没有抛出异常
        // assertThrows(IllegalAccessException.class, () -> nameField.get(person));
    }

    @Test
    public void whenAccessingWrongProperty_thenNoSuchFieldException() throws Exception {
        Person person = new Person();

        assertThrows(NoSuchFieldException.class, () -> person.getClass()
            .getDeclaredField("firstName"));
    }

    @Test
    public void whenAccessingNullProperty_thenNullPointerException() throws Exception {
        Person person = new Person();

        assertThrows(NullPointerException.class, () -> person.getClass()
            .getDeclaredField(null));
    }

    @Getter
    @Setter
    public static class Person {
        private String name = "John";
        private byte age = 30;
        private short uidNumber = 5555;
        private int pinCode = 452002;
        private long contactNumber = 123456789L;
        private float height = 6.1242f;
        private double weight = 75.2564;
        private char gender = 'M';
        private boolean active = true;
    }
    
}
