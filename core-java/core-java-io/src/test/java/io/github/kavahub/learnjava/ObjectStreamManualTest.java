package io.github.kavahub.learnjava;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectStreamManualTest {
    @Test
    @DisplayName("When a BadThing object is deserialized, then code execution in MyCustomAttackObject is run.")
    public void givenABadThingObject_whenItsDeserialized_thenExecutionIsRun() throws Exception {
        BadThing bt = new BadThing();

        bt.looselyDefinedThing = new MyCustomAttackObject();
        bt.methodName = "methodThatTriggersAttack";

        byte[] serializedObject = serialize(bt);

        try (InputStream bis = new ByteArrayInputStream(serializedObject);
             ObjectInputStream ois = new ObjectInputStream(bis)) {

            ois.readObject(); // malicious code is run
        }
    }

    private static byte[] serialize(Object object) throws Exception {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(object);
            oos.flush();
            return bos.toByteArray();
        }
    }
    
    public static class MyCustomAttackObject implements Serializable {
        public static void methodThatTriggersAttack() {
            log.info("methodThatTriggersAttack");
            try {
                Runtime.getRuntime().exec("echo \"Oh, no! I've been hacked\"");
            } catch (IOException e) {
                // handle error...
            }
        }
    
    }

    
    public static class BadThing implements Serializable {
        private static final long serialVersionUID = 0L;
    
        Object looselyDefinedThing;
        String methodName;
    
        private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
            log.info("readOBject");

            ois.defaultReadObject();
            try {
                Method method = looselyDefinedThing.getClass().getMethod(methodName);
                method.invoke(looselyDefinedThing);
            } catch (Exception e) {
                // handle error...
            }
        }
    
        private void writeObject(ObjectOutputStream oos) throws IOException {
            log.info("writeObject");
            oos.defaultWriteObject();
        }
    }
}
