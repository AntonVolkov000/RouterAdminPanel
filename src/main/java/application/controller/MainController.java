package application.controller;

import application.model.AdminAccount;
import application.model.GeneralConfig;
import application.services.AdminAccountService;
import application.services.ConfigService;
import application.services.GeneralConfigService;
import application.services.WifiService;
import application.services.notifications.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    private final GeneralConfigService generalConfigService;
    private final WifiService wifiService;
    private final ConfigService configService;
    private final AdminAccountService adminAccountService;

    private final SendEmailService sendEmailService;
    private final String subject = "Перебои в сети интернет";
    private final String textPart1 = "Здравствуйте, ";
    private final String textPart2 = "К сожалению, в данный момент по вашему адресу обнаружен обрыв в сети интернет. Не волнуйтесь, мы уже занимаемся этим вопросом. Интернет будет восстановлен в течение 3 часов.";

    @Autowired
    public MainController(
            GeneralConfigService generalConfigService,
            WifiService wifiService,
            ConfigService configService,
            AdminAccountService adminAccountService,
            SendEmailService sendEmailService) {
        this.generalConfigService = generalConfigService;
        this.wifiService = wifiService;
        this.configService = configService;
        this.adminAccountService = adminAccountService;
        this.sendEmailService = sendEmailService;
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
        sendEmailService.notifyAdminAccountRecipients(adminAccount, subject, text);
        return "redirect:/";
    }
}
