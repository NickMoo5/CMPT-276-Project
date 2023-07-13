package sfu.cmpt276.project.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage; 

public class emailUtility {
    private static final String senderEmail = "wayfinderpwreset@gmail.com"; 
    private static final String senderPassword = "mdlqobgjamdhvdbc"; 

    public static void sendEmail(String sendTo, String subject, String body) {
        Properties properties = System.getProperties();

        //setup SMTP server
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        //create session obj and pass username/password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            //set header(from)
            message.setFrom(new InternetAddress(senderEmail));
            //set header(to)
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
            //set subject
            message.setSubject(subject);
            //set body
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
