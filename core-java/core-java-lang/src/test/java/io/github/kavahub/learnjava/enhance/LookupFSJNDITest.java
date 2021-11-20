package io.github.kavahub.learnjava.enhance;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import javax.naming.NamingException;

import org.junit.jupiter.api.Test;

public class LookupFSJNDITest {
    LookupFSJNDI fsjndi;
    final String FILENAME = "test.find";

    public LookupFSJNDITest() {
        try {
            fsjndi = new LookupFSJNDI();
        } catch (NamingException e) {
            e.printStackTrace();
            fsjndi = null;
        }
    }

    @Test
    public void whenInitializationLookupFSJNDIIsNotNull_thenSuccess() {
        assertNotNull(fsjndi);
    }

    @Test
    public void givenLookupFSJNDI_whengetInitialContextIsNotNull_thenSuccess() {
        assertNotNull(fsjndi.getCtx());
    }

    @Test
    public void givenInitialContext_whenLokupFileExists_thenSuccess() {
        File file = fsjndi.getFile(FILENAME);
        assertNotNull(file);
    }
}
