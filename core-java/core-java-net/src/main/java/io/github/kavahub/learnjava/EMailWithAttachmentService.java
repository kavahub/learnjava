package io.github.kavahub.learnjava;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EMailWithAttachmentService {
    private String username = "";
    private String password = "";
    private String host = "";
    private int port;

    EMailWithAttachmentService() {
    }

    EMailWithAttachmentService(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        return session;
    }

    public Message createMail(Session session) throws AddressException, MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("mail@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mail@gmail.com"));
        message.setSubject("Testing Subject");

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("This is message body");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        MimeBodyPart attachmentPart = new MimeBodyPart();
        MimeBodyPart attachmentPart2 = new MimeBodyPart();

        attachmentPart.attachFile(new File("pom.xml"));
        attachmentPart2.attachFile(new File("pom.xml"));

        multipart.addBodyPart(attachmentPart);
        multipart.addBodyPart(attachmentPart2);

        message.setContent(multipart);

        return message;
    }

    public void sendMail(Session session) throws MessagingException, IOException {
        Message message = createMail(session);
        Transport.send(message);
    } 
    
    
    public static void main(String ... args) throws AddressException, MessagingException, IOException {
        EMailWithAttachmentService service = new EMailWithAttachmentService("your_username", "your_password", "smtp.ym.163.com", 25);
        Session session = service.getSession();
        service.sendMail(session);
    }
}
