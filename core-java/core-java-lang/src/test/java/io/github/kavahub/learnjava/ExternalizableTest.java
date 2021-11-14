package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ExternalizableTest {
    private final static String OUTPUT_FILE = "externalizable.txt";

    @AfterAll
    public static void clear() throws IOException {
        Path path = Paths.get(OUTPUT_FILE);
        Files.deleteIfExists(path);
    }

    @Test
    public void whenSerializing_thenUseExternalizable() throws IOException, ClassNotFoundException {

        Country c = new Country();
        c.setCapital("Yerevan");
        c.setCode(374);
        c.setName("Armenia");

        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_FILE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        c.writeExternal(objectOutputStream);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(OUTPUT_FILE);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Country c2 = new Country();
        c2.readExternal(objectInputStream);

        objectInputStream.close();
        fileInputStream.close();

        assertTrue(c2.getCode() == c.getCode());
        assertTrue(c2.getName().equals(c.getName()));
    }

    @Test
    public void whenInheritanceSerialization_then_UseExternalizable() throws IOException, ClassNotFoundException {

        Region r = new Region();
        r.setCapital("Yerevan");
        r.setCode(374);
        r.setName("Armenia");
        r.setClimate("Mediterranean");
        r.setPopulation(120.000);

        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_FILE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        r.writeExternal(objectOutputStream);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(OUTPUT_FILE);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Region r2 = new Region();
        r2.readExternal(objectInputStream);

        objectInputStream.close();
        fileInputStream.close();

        assertTrue(r2.getPopulation() == null);
    } 

    @Getter
    @Setter
    @ToString
    public static class Community implements Serializable {
        private int id;
    }
    
    /**
     * 可以指定序列化字段
     */
    @Getter
    @Setter
    @ToString
    public static class Country implements Externalizable {
    
        private String name;
        private String capital;
        private int code;
    
        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeUTF(name);
            out.writeUTF(capital);
            out.writeInt(code);
        }
    
        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            this.name = in.readUTF();
            this.capital = in.readUTF();
            this.code = in.readInt();
        }
    }
    
    @Getter
    @Setter
    @ToString
    public class Region extends Country {    
        private String climate;
        private Double population;
        private Community community;
    
        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            super.writeExternal(out);
            out.writeUTF(climate);
            community = new Community();
            community.setId(5);
            out.writeObject(community);
        }
    
        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            super.readExternal(in);
            this.climate = in.readUTF();
            community = (Community) in.readObject();
        }
    
    }
}
