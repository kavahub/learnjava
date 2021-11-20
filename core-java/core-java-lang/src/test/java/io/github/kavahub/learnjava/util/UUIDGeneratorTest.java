package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.UUIDGenerator.*;

public class UUIDGeneratorTest {
    private static final String NAMESPACE_URL = "6ba7b811-9dad-11d1-80b4-00c04fd430c8";
    private static final String NAMESPACE_DNS = "6ba7b810-9dad-11d1-80b4-00c04fd430c8";

    @Test
    public void version_1_UUID_is_generated_with_correct_length_version_and_variant() {

        UUID uuid = generateType1UUID();

        assertEquals(36, uuid.toString().length());
        assertEquals(1, uuid.version());
        assertEquals(2, uuid.variant());
    }

    @Test
    public void version_3_UUID_is_correctly_generated_for_domain() throws UnsupportedEncodingException {

        UUID uuid = generateType3UUID(NAMESPACE_DNS, "learnjava.com");

        assertEquals("9e743923-d709-3c33-9dd7-a34c25ecc1ae", uuid.toString());
        assertEquals(3, uuid.version());
        assertEquals(2, uuid.variant());
    }

    @Test
    public void version_3_UUID_is_correctly_generated_for_domain_d() throws UnsupportedEncodingException {

        UUID uuid = generateType3UUID(NAMESPACE_DNS, "d");

        assertEquals("dbd41ecb-f466-33de-b309-1468addfc63b", uuid.toString());
        assertEquals(3, uuid.version());
        assertEquals(2, uuid.variant());
    }

    @Test
    public void version_4_UUID_is_generated_with_correct_length_version_and_variant() {

        UUID uuid = generateType4UUID();

        assertEquals(36, uuid.toString().length());
        assertEquals(4, uuid.version());
        assertEquals(2, uuid.variant());
    }

    @Test
    public void version_5_UUID_is_correctly_generated_for_domain() throws UnsupportedEncodingException {

        UUID uuid = generateType5UUID(NAMESPACE_URL, "learnjava.com");

        assertEquals("b09b7d5b-4efc-54ae-83ff-8a8df9c89d79", uuid.toString());
        assertEquals(5, uuid.version());
        assertEquals(2, uuid.variant());
    }

    @Test
    public void version_5_UUID_is_correctly_generated_for_domai() {

        UUID uuid = UUIDGenerator.generateType5UUID("learnjava.net");

        assertEquals("4ecb16e4-f67a-5e2b-8ad0-329e94f76fe2", uuid.toString());
        assertEquals(5, uuid.version());
        assertEquals(2, uuid.variant());
    }    
}
