package application.controller;

import application.model.AdminAccount;
import application.model.GeneralConfig;
import application.model.Recipient;
import application.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Controller
public class MainController {
    private final GeneralConfigService generalConfigService;
    private final WifiService wifiService;
    private final ConfigService configService;
    private final AdminAccountService adminAccountService;
    @Value("${MAIL_SENDER}")
    private String mailSender;
    @Value("${MAIL_PASSWORD}")
    private String mailPassword;
    private final String subject = "Перебои в сети интернет";
    private final String textPart1 = "Здравствуйте, ";
    private final String textPart2 = "К сожалению, в данный момент по вашему адресу обнаружен обрыв в сети интернет. Не волнуйтесь, мы уже занимаемся этим вопросом. Интернет будет восстановлен в течение 3 часов.";

    @Autowired
    public MainController(
            GeneralConfigService generalConfigService,
            WifiService wifiService,
            ConfigService configService,
            AdminAccountService adminAccountService
    ) {
        this.generalConfigService = generalConfigService;
        this.wifiService = wifiService;
        this.configService = configService;
        this.adminAccountService = adminAccountService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String adminAccountLogin = auth.getName();
        GeneralConfig generalConfig = generalConfigService.getGeneralConfigByAdminLogin(adminAccountLogin);
        wifiService.prepapreWifi(generalConfig.getWifi().getWifiId());
        configService.prepareConfig(generalConfig.getConfig().getConfigId());
        model.addAttribute("generalConfig", generalConfig);
        return "index";
    }

    @RequestMapping("/disconnect-internet/{id}")
    public String disconnectInternet(@PathVariable("id") long id) {
        AdminAccount adminAccount = adminAccountService.getAdminAccountById(id);
        String text = textPart1 + adminAccount.getLogin() + "!\n" + textPart2;
        List<Recipient> recipients = adminAccountService.getAllRecipients(id);
        StringBuilder recipientsSB = new StringBuilder();
        for (Recipient recipient: recipients) {
            recipientsSB.append(recipient.getEmail());
        }
        sendEmail(recipientsSB.toString(), subject, text);
        return "redirect:/";
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
