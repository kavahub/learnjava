package io.github.kavahub.learnjava;

import java.io.File;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 * 发送邮件
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class EmailServiceExample {
    private String host = "";
    private int port = 0;
    private String username = "";
    private String password = "";


    public EmailServiceExample(String host, int port, String username, String password) {

        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;

        sendMail();
    }

    private void sendMail() {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", host);

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

                Message message = new MimeMessage(session);
                // 发送人
                message.setFrom(new InternetAddress("from@gmail.com"));
                // 收件人
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("to@gmail.com"));
                message.setSubject("Mail Subject");

                String msg = "This is my first email using JavaMailer";

                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(msg, "text/html");

                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.attachFile(new File("pom.xml"));

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);
                multipart.addBodyPart(attachmentBodyPart);

                message.setContent(multipart);

                Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String ... args) {
        new EmailServiceExample("smtp.ym.163.com", 25, "your_username", "your_password");
    }  
}
