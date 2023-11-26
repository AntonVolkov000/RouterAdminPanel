package application.controller;

import application.model.*;
import application.services.ConfigService;
import application.services.GeneralConfigService;
import application.services.WifiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/general-config")
public class GeneralConfigController {
    private final GeneralConfigService generalConfigService;
    private final WifiService wifiService;
    private final ConfigService configService;
    @Autowired
    public GeneralConfigController(
            GeneralConfigService generalConfigService,
            WifiService wifiService,
            ConfigService configService
    ) {
        this.generalConfigService = generalConfigService;
        this.wifiService = wifiService;
        this.configService = configService;
    }

    @GetMapping("/{id}")
    public String configAndWifiInfo(@PathVariable("id") long id, Model model) {
        GeneralConfig generalConfig = generalConfigService.getGeneralConfigById(id);
        wifiService.prepapreWifi(generalConfig.getWifi().getWifiId());
        configService.prepareConfig(generalConfig.getConfig().getConfigId());
        Config config = generalConfig.getConfig();
        Wifi wifi = generalConfig.getWifi();
        model.addAttribute("config", config);
        model.addAttribute("wifi", wifi);
        return "general-config";
    }
}
