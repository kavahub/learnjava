package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.AuthorizeCallback;
import javax.security.sasl.RealmCallback;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 
 * @see <a href="https://blog.csdn.net/asdfsadfasdfsa/article/details/104526427/">CSDN</a>
 */
public class SaslTest {
    private static final String MECHANISM = "DIGEST-MD5";
    private static final String SERVER_NAME = "myServer";
    private static final String PROTOCOL = "myProtocol";
    private static final String AUTHORIZATION_ID = null;
    private static final String QOP_LEVEL = "auth-conf";

    private static SaslServer saslServer;
    private static SaslClient saslClient;

    @BeforeAll
    public static void setUp() throws SaslException {

        ServerCallbackHandler serverHandler = new ServerCallbackHandler();
        ClientCallbackHandler clientHandler = new ClientCallbackHandler();

        Map<String, String> props = new HashMap<>();
        props.put(Sasl.QOP, QOP_LEVEL);

        saslServer = Sasl.createSaslServer(MECHANISM, PROTOCOL, SERVER_NAME, props, serverHandler);
        saslClient = Sasl.createSaslClient(new String[] { MECHANISM }, AUTHORIZATION_ID, PROTOCOL, SERVER_NAME, props, clientHandler);

    }

    @Test
    public void givenHandlers_whenStarted_thenAutenticationWorks() throws SaslException {

        byte[] challenge;
        byte[] response;

        challenge = saslServer.evaluateResponse(new byte[0]);
        response = saslClient.evaluateChallenge(challenge);

        challenge = saslServer.evaluateResponse(response);
        response = saslClient.evaluateChallenge(challenge);

        assertTrue(saslServer.isComplete());
        assertTrue(saslClient.isComplete());

        String qop = (String) saslClient.getNegotiatedProperty(Sasl.QOP);
        assertEquals("auth-conf", qop);

        // 客户端发送消息
        byte[] outgoing = "Java".getBytes();
        byte[] secureOutgoing = saslClient.wrap(outgoing, 0, outgoing.length);

        // 服务器端处理
        byte[] secureIncoming = secureOutgoing;
        byte[] incoming = saslServer.unwrap(secureIncoming, 0, secureIncoming.length);
        assertEquals("Java", new String(incoming, StandardCharsets.UTF_8));
    }

    @AfterAll
    public static void tearDown() throws SaslException {
        if (saslClient != null) {
            saslClient.dispose();
        }

        if (saslServer != null) {
            saslServer.dispose();
        }
    }  


    public static class ServerCallbackHandler implements CallbackHandler {

        @Override
        public void handle(Callback[] cbs) throws IOException, UnsupportedCallbackException {
            for (Callback cb : cbs) {
                if (cb instanceof AuthorizeCallback) {
                    AuthorizeCallback ac = (AuthorizeCallback) cb;
                    ac.setAuthorized(true);
                } else if (cb instanceof NameCallback) {
                    NameCallback nc = (NameCallback) cb;
                    nc.setName("username");
    
                } else if (cb instanceof PasswordCallback) {
                    PasswordCallback pc = (PasswordCallback) cb;
                    pc.setPassword("password".toCharArray());
                } else if (cb instanceof RealmCallback) {
                    RealmCallback rc = (RealmCallback) cb;
                    rc.setText("myServer");
                }
            }
        }
    }

    public static class ClientCallbackHandler implements CallbackHandler {

        @Override
        public void handle(Callback[] cbs) throws IOException, UnsupportedCallbackException {
            for (Callback cb : cbs) {
                if (cb instanceof NameCallback) {
                    NameCallback nc = (NameCallback) cb;
                    nc.setName("username");
                } else if (cb instanceof PasswordCallback) {
                    PasswordCallback pc = (PasswordCallback) cb;
                    pc.setPassword("password".toCharArray());
                } else if (cb instanceof RealmCallback) {
                    RealmCallback rc = (RealmCallback) cb;
                    rc.setText("myServer");
                }
            }
        }
    }
    
}
