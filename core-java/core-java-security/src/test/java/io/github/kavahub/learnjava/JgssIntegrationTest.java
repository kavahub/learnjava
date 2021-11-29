package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.security.sasl.SaslException;

import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.MessageProp;
import org.ietf.jgss.Oid;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 
 * org.ietf.jgss 该软件包提供了一个框架，允许应用程序开发人员使用统一的API从各种底层安全机制（如Kerberos）中使用安全服务，
 * 如身份验证，数据完整性和数据机密性。 应用程序可以选择使用的安全机制用唯一对象标识符标识。 这种机制的一个示例是Kerberos v5
 * GSS-API机制（对象标识符1.2.840.113554.1.2.2）。 此机制可通过GSSManager类的默认实例获得。
 *
 * @author PinWei Wan
 * @since 1.0.0
 * 
 * @see <a href="https://www.baeldung.com/java-gss">A Guide to Java GSS API</a>
 */
@Disabled
public class JgssIntegrationTest {
    private static final String SERVER_PRINCIPAL = "HTTP/localhost@EXAMPLE.COM";
    private static final String MECHANISM = "1.2.840.113554.1.2.2";

    private static GSSContext serverContext;
    private static GSSContext clientContext;

    @BeforeAll
    public static void setUp() throws SaslException, GSSException {
        GSSManager manager = GSSManager.getInstance();
        serverContext = manager.createContext((GSSCredential) null);
        String serverPrinciple = SERVER_PRINCIPAL;
        GSSName serverName = manager.createName(serverPrinciple, null);
        Oid krb5Oid = new Oid(MECHANISM);
        clientContext = manager.createContext(serverName, krb5Oid, (GSSCredential) null, GSSContext.DEFAULT_LIFETIME);
        clientContext.requestMutualAuth(true);
        clientContext.requestConf(true);
        clientContext.requestInteg(true);
    }

    @Test
    public void givenCredential_whenStarted_thenAutenticationWorks() throws SaslException, GSSException {
        byte[] serverToken;
        byte[] clientToken;

        // On the client-side
        clientToken = clientContext.initSecContext(new byte[0], 0, 0);
        // sendToServer(clientToken); // This is supposed to be send over the network

        // On the server-side
        serverToken = serverContext.acceptSecContext(clientToken, 0, clientToken.length);
        // sendToClient(serverToken); // This is supposed to be send over the network

        // Back on the client-side
        clientContext.initSecContext(serverToken, 0, serverToken.length);

        assertTrue(serverContext.isEstablished());
        assertTrue(clientContext.isEstablished());
    }

    @Test
    public void givenContext_whenStarted_thenSecurityWorks() throws SaslException, GSSException {
        // On the client-side
        byte[] messageBytes = "a message".getBytes();
        MessageProp clientProp = new MessageProp(0, true);
        byte[] clientToken = clientContext.wrap(messageBytes, 0, messageBytes.length, clientProp);
        // sendToServer(clientToken); // This is supposed to be send over the network

        // On the server-side
        MessageProp serverProp = new MessageProp(0, false);
        byte[] bytes = serverContext.unwrap(clientToken, 0, clientToken.length, serverProp);
        String string = new String(bytes);

        assertEquals("a message", string);
    }

    @AfterAll
    public static void tearDown() throws SaslException, GSSException {
        serverContext.dispose();
        clientContext.dispose();
    }
}
