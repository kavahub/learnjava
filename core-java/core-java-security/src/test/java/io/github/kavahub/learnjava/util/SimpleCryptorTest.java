package io.github.kavahub.learnjava.util;

import static io.github.kavahub.learnjava.util.SimpleCryptor.decryptMessage;
import static io.github.kavahub.learnjava.util.SimpleCryptor.encryptMessage;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SimpleCryptorTest {
    private static String encKeyString;
    private static String message;

    @BeforeAll
    public static void init(){
        encKeyString =  "1234567890123456";
        message = "This is a secret message";
    }

    @Test
    public void givenEncryptionKey_whenMessageIsPassedToEncryptor_thenMessageIsEncrypted() throws Exception {
        byte[] encryptedMessage = encryptMessage(message.getBytes(),encKeyString.getBytes());

        assertThat(encryptedMessage).isNotNull();
        assertThat(encryptedMessage.length  % 32).isEqualTo(0);
    }

    @Test
    public void givenEncryptionKey_whenMessageIsEncrypted_thenDecryptMessage() throws Exception{
        byte[] encryptedMessageBytes = encryptMessage(message.getBytes(),encKeyString.getBytes());

        byte[] clearMessageBytes = decryptMessage(encryptedMessageBytes, encKeyString.getBytes());

        assertThat(message).isEqualTo(new String(clearMessageBytes));
    }    
}
