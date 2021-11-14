package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URL;
import java.security.AccessControlException;
import java.security.BasicPermission;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("无法运行")
public class SecurityManagerTest {
    private static final String TESTING_SECURITY_POLICY = "file:src/test/resources/testing.policy";

    @BeforeAll
    public static void setUp() {
        System.setProperty("java.security.policy", TESTING_SECURITY_POLICY);
        System.setSecurityManager(new SecurityManager());
    }

    @AfterAll
    public static void tearDown() {
        System.setSecurityManager(null);
    }

    @Test
    public void whenSecurityManagerIsActive_thenNetworkIsNotAccessibleByDefault() throws IOException {
        assertThrows(AccessControlException.class, () -> new URL("http://www.google.com").openConnection().connect());
    }

    @Test
    public void whenUnauthorizedClassTriesToAccessProtectedOperation_thenAnExceptionIsThrown() {
        assertThrows(AccessControlException.class, () -> new Service().operation());
    }

    public static class Service {

        public static final String OPERATION = "my-operation";

        public void operation() {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(new CustomPermission(OPERATION));
            }
            System.out.println("Operation is executed");
        }

        public static void main(String[] args) {
            new Service().operation();
        }
    }

    public static class CustomPermission extends BasicPermission {
        public CustomPermission(String name) {
            super(name);
        }

        public CustomPermission(String name, String actions) {
            super(name, actions);
        }
    }
}
