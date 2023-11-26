package application.services.notifications;

import application.model.AdminAccount;
import application.model.Recipient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Component
public class SendEmailService {
    @Value("${MAIL_SENDER}")
    private String mailSender;
    @Value("${MAIL_PASSWORD}")
    private String mailPassword;

    public void notifyAdminAccountRecipients(AdminAccount adminAccount, String subject, String message)
    {
        List<String> emails = adminAccount.getRecipients().stream()
                .map(Recipient::getEmail)
                .toList();
        if (emails.isEmpty())
            return;
        sendEmail(String.join(",", emails), subject, message);
    }

    public void sendEmail(String recipients, String subject, String text) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        try {
            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailSender, mailPassword);
                }
            };
            Session session = Session.getDefaultInstance(props, authenticator);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailSender));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            msg.setSubject(subject);
            msg.setText(text);
            Transport.send(msg);
        } catch (MessagingException e){
            e.printStackTrace();
        }
    }
}
