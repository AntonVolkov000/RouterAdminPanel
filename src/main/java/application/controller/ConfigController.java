package application.controller;

import application.model.Config;
import application.services.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/config")
public class ConfigController {
    private final ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }
    @GetMapping("/update/{id}")
    public String updateConfig(@PathVariable("id") long id, Model model) {
        Config config = configService.updateConfig(id);
        model.addAttribute("config", config);
        return "main-config";
    }
}
