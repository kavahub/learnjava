package io.github.kavahub.learnjava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jakarta.mail.Address;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * 邮件附件下载
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class EmailAttachmentsDownloadExample {
    private String downloadDirectory;

    public void setSaveDirectory(String dir) {
        this.downloadDirectory = dir;
    }

    public void downloadEmailAttachments(String host, String port, String userName, String password) {
        Properties properties = setMailServerProperties(host, port);

        Store store = null;
        Folder inbox = null;
        try {
            store = setSessionStoreProperties(userName, password, properties);
            inbox = store.getFolder("INBOX");

            inbox.open(Folder.READ_ONLY);
            Message[] arrayMessages = inbox.getMessages();
            for (int i = 0; i < arrayMessages.length; i++) {
                Message message = arrayMessages[i];
                Address[] fromAddress = message.getFrom();
                String from = fromAddress[0].toString();
                String subject = message.getSubject();
                String sentDate = message.getSentDate().toString();
                List<String> attachments = new ArrayList<String>();
                if (message.getContentType().contains("multipart")) {
                    attachments = downloadAttachments(message);
                }

                System.out.println("Message #" + (i + 1) + ":");
                System.out.println(" From: " + from);
                System.out.println(" Subject: " + subject);
                System.out.println(" Sent Date: " + sentDate);
                System.out.println(" Attachments: " + attachments);
            }
        } catch (NoSuchProviderException | MessagingException | IOException e) {
            log.error("download email attachments exception", e);
        } finally {
            try {
                if (inbox != null) {
                    inbox.close(false);
                }

                if (store != null) {
                    store.close();
                }
            } catch (MessagingException e) {
                log.error("close exception", e);
            }
        }
    }

    public List<String> downloadAttachments(Message message) throws IOException, MessagingException {
        List<String> downloadedAttachments = new ArrayList<String>();
        Multipart multiPart = (Multipart) message.getContent();
        int numberOfParts = multiPart.getCount();
        for (int partCount = 0; partCount < numberOfParts; partCount++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                // 中文附件乱码
                String file = MimeUtility.decodeText(part.getFileName());
                part.saveFile(downloadDirectory + File.separator + file);
                downloadedAttachments.add(file);
            }
        }

        return downloadedAttachments;
    }

    public Store setSessionStoreProperties(String userName, String password, Properties properties)
            throws NoSuchProviderException, MessagingException {
        Session session = Session.getDefaultInstance(properties);

        Store store = session.getStore("pop3");
        store.connect(userName, password);
        return store;
    }

    private Properties setMailServerProperties(String host, String port) {
        Properties properties = new Properties();

        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", port);

        properties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.pop3.socketFactory.fallback", "false");
        properties.setProperty("mail.pop3.socketFactory.port", String.valueOf(port));
        return properties;
    }


    public static void main(String[] args) throws IOException {
        String host = "pop.ym.163.com";
        String port = "995";
        String userName = "your_username";
        String password = "your_password";

        String saveDirectory = "EmailAttachmentsDownloadExample";

        Path path = Paths.get(saveDirectory);
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }

        EmailAttachmentsDownloadExample receiver = new EmailAttachmentsDownloadExample();
        receiver.setSaveDirectory(saveDirectory);
        receiver.downloadEmailAttachments(host, port, userName, password);
    }
}
