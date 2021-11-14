package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;

public class SerializingAndDeserializingObjectTest {
    private final static String OUTPUT_YOUR_FILE = "yourfile.txt";
    private final static String OUTPUT_YOUR_FILE_1 = "yourfile1.txt";

    @AfterAll
    public static void clear() throws IOException {
        Path path = Paths.get(OUTPUT_YOUR_FILE);
        Files.deleteIfExists(path);

        path = Paths.get(OUTPUT_YOUR_FILE_1);
        Files.deleteIfExists(path);
    }
    
    @Test
    public void whenSerializingAndDeserializing_ThenObjectIsTheSame() throws IOException, ClassNotFoundException {
        Person p = new Person();
        p.setAge(20);
        p.setName("Joe");

        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_YOUR_FILE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(p);
        objectOutputStream.flush();
        objectOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(OUTPUT_YOUR_FILE);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Person p2 = (Person) objectInputStream.readObject();
        objectInputStream.close();

        assertTrue(p2.getAge() == p.getAge());
        assertTrue(p2.getName().equals(p.getName()));
    }

    @Test
    public void whenCustomSerializingAndDeserializing_ThenObjectIsTheSame() throws IOException, ClassNotFoundException {
        Person p = new Person();
        p.setAge(20);
        p.setName("Joe");

        Address a = new Address();
        a.setHouseNumber(1);

        Employee e = new Employee();
        e.setPerson(p);
        e.setAddress(a);

        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_YOUR_FILE_1);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(e);
        objectOutputStream.flush();
        objectOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(OUTPUT_YOUR_FILE_1);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Employee e2 = (Employee) objectInputStream.readObject();
        objectInputStream.close();

        assertTrue(e2.getPerson().getAge() == e.getPerson().getAge());
        assertTrue(e2.getAddress().getHouseNumber() == (e.getAddress().getHouseNumber()));
    }  

    @Getter
    @Setter
    public static class Person implements Serializable {
        private int age;
        private String name;
    }

    @Getter
    @Setter
    public static class Address implements Serializable {
        private int houseNumber;
    }

    @Getter
    @Setter
    public static class Employee implements Serializable {
        private Person person;
        private Address address;
    }
}
