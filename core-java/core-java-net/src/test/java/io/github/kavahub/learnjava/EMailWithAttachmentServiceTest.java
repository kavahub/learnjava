package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Session;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 
 * 下载邮件附件
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class EMailWithAttachmentServiceTest {
    private static EMailWithAttachmentService emailService;
    private static GreenMail greenMail;

    @BeforeAll
    public static void startMailServer() {
        emailService = new EMailWithAttachmentService();
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();
    }

    @AfterAll
    public static void stopMailServer() {
        greenMail.stop();
        emailService = null;
    }

    @Test
    public void canSendMail() throws MessagingException, IOException {
        Session testSession = greenMail.getSmtp().createSession();
        emailService.sendMail(testSession);
        assertEquals(1, greenMail.getReceivedMessages().length);

    }
}
