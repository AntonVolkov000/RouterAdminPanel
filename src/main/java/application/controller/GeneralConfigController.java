package application.controller;

import application.model.*;
import application.services.ConfigService;
import application.services.ConnectionInternetTypeService;
import application.services.GeneralConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;;

@Controller
@RequestMapping("/generalConfig")
public class GeneralConfigController {
    private final GeneralConfigService generalConfigService;
    @Autowired
    public GeneralConfigController(
            GeneralConfigService generalConfigService) {
        this.generalConfigService = generalConfigService;
    }

    @GetMapping("/{id}")
    public String configAndWifiInfo(@PathVariable("id") long id, Model model) {
        GeneralConfig generalConfig = generalConfigService.getGeneralConfigById(id);
        Config config = generalConfig.getConfig();
        Wifi wifi = generalConfig.getWifi();
        model.addAttribute("config", config);
        model.addAttribute("wifi", wifi);
        return "general-config";
    }
}
