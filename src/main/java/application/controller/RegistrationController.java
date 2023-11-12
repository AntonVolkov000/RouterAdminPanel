package application.controller;

import application.model.Config;
import application.model.GeneralConfig;
import application.model.Wifi;
import application.services.ConfigService;
import application.services.GeneralConfigService;
import application.services.WifiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {
    private final GeneralConfigService generalConfigService;
    private final WifiService wifiService;
    private final ConfigService configService;
    @Autowired
    public RegistrationController(GeneralConfigService generalConfigService, WifiService wifiService, ConfigService configService) {
        this.generalConfigService = generalConfigService;
        this.wifiService = wifiService;
        this.configService = configService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String adminAccountLogin = auth.getName();
        GeneralConfig generalConfig = prepareGeneralConfig(adminAccountLogin);
        model.addAttribute("generalConfig", generalConfig);
        return "index";
    }

    private GeneralConfig prepareGeneralConfig(String adminAccountLogin){
        GeneralConfig generalConfig = generalConfigService.getGeneralConfigByAdminLogin(adminAccountLogin);
        Wifi wifi = wifiService.prepapreWifi(generalConfig.getWifi().getWifiId());
        Config config = configService.prepareConfig(generalConfig.getConfig().getConfigId());
        return generalConfigService.updateConfigs(generalConfig, wifi, config);
    }
}
