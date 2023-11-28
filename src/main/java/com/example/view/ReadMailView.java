package com.example.view;

import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Properties;

@ManagedBean(name = "readMailView")
@RequestScoped
@Controller
public class ReadMailView {
    private String emailContent = "";

    private String username = "jagadishchintanippu@gmail.com";
    private String password = "vpbx ysxm mmlm csvy";   //vpbx ysxm mmlm csvy

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;

    }
    public String getEmailContent(Message message) throws MessagingException, IOException {
        System.out.println("Jagadish tests");
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
        }
        return "";
        /*try {
            System.out.println("Jagadish tests");

            if (message.isMimeType("text/plain")) {
                System.out.println("Content Type: text/plain");
                Object content = message.getContent();
                if (content instanceof String) {
                    return (String) content;
                } else {
                    // Handle the case where getContent returns null or unexpected type
                    System.out.println("Unexpected content type for text/plain: " + content);
                }
            } else if (message.isMimeType("multipart/*")) {
                System.out.println("Content Type: multipart/*");
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                // Additional logic to handle multipart content if needed
            } else {
                System.out.println("Unsupported Content Type: " + message.getContentType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";*/
    }
    public String readEmails() {

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", "imap.gmail.com");
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "jagadishchintanippu@gmail.com", "vpbx ysxm mmlm csvy");// Specify the Username and the PassWord
                    }
                });

        try {
            Store store = session.getStore("imaps");
            store.connect(username, password);
            Folder inbox = store.getFolder("inbox");
            System.out.println("unread count - " + inbox.getUnreadMessageCount());
            inbox.open(Folder.READ_ONLY);
          //  Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.RECENT), false));
           // List<Message> messages = Arrays.asList(inbox.getMessages());

            FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message[] messages = inbox.search(flagTerm);
            for (Message message : messages) {
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Sent Date: " + message.getSentDate());
                System.out.println("-----------------------------");
                // System.out.println("Text: " + getTextFromMessage(message));
                String content = "Subject: " + message.getSubject() + "\n" +
                        "From: " + message.getFrom()[0] + "\n" +
                        "Sent Date: " + message.getSentDate() + "\n\n";

                emailContent += content + "\n";
                    setEmailContent(emailContent);
                //getEmailContent(message);
            }

            inbox.close(true);
            store.close();
            return "";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "error"; // handle error in JSF page
        }
    }

    private String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return getTextFromMimeMultipart(mimeMultipart);
        }
        return "";
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        int count = mimeMultipart.getCount();
        if (count == 0) {
            return "";
        }

        boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
        if (multipartAlt) {
            // alternatives appear in an order of increasing
            // faithfulness to the original content. Customize as needed.
            return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            result.append(getTextFromBodyPart(bodyPart));
        }
        return result.toString();
    }

    private String getTextFromBodyPart(BodyPart bodyPart) throws Exception {
        if (bodyPart.isMimeType("text/plain")) {
            return bodyPart.getContent().toString();
        } else if (bodyPart.isMimeType("text/html")) {
            // handle HTML content
            return "<html><body>" + bodyPart.getContent() + "</body></html>";
        } else if (bodyPart.getContent() instanceof MimeMultipart) {
            return getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
        }
        return "";
    }

}
