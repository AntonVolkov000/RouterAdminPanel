package application.controller;

import application.model.Config;
import application.model.ConnectionInternetType;
import application.services.ConfigService;
import application.services.ConnectionInternetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/config")
public class ConfigController {
    private final ConfigService configService;
    private final ConnectionInternetTypeService connectionInternetTypeService;

    @Autowired
    public ConfigController(ConfigService configService, ConnectionInternetTypeService connectionInternetTypeService) {
        this.configService = configService;
        this.connectionInternetTypeService = connectionInternetTypeService;
    }

    @GetMapping("/{id}")
    public String configById(@PathVariable("id") long id, Model model) {
        Boolean isStaticConfig = configService.isStaticConfigById(id);
        List<ConnectionInternetType> connectionInternetTypes = connectionInternetTypeService.getAllConnectionInternetTypes();
        model.addAttribute("connectionInternetTypes", connectionInternetTypes);
        if (isStaticConfig) {
            Config config = configService.getConfigById(id);
            model.addAttribute("config", config);
            return "config-static";
        } else {
            Config config = configService.prepareConfig(id);
            model.addAttribute("config", config);
            return "config-dynamic";
        }
    }

    @GetMapping("/update/{id}")
    public String updateConfig(@PathVariable("id") long id, Model model) {
        Config config = configService.updateConfig(id);
        model.addAttribute("config", config);
        List<ConnectionInternetType> connectionInternetTypes = connectionInternetTypeService.getAllConnectionInternetTypes();
        model.addAttribute("connectionInternetTypes", connectionInternetTypes);
        return "config-dynamic";
    }

    @PostMapping("/change/{id}")
    public String changeConfig(@PathVariable("id") long id, String ip, String mask, String gate, Model model) {
        Config config = configService.changeConfig(id, ip, mask, gate);
        model.addAttribute("config", config);
        List<ConnectionInternetType> connectionInternetTypes = connectionInternetTypeService.getAllConnectionInternetTypes();
        model.addAttribute("connectionInternetTypes", connectionInternetTypes);
        return "config-static";
    }

    @GetMapping("/{id}/connection-internet-type/{connectionInternetTypeId}")
    public String updateConnectionInternetType(
            @PathVariable("id") long id,
            @PathVariable("connectionInternetTypeId") long connectionInternetTypeId,
            Model model
    ) {
        Config config = configService.updateConnectionInternetType(id, connectionInternetTypeId);
        model.addAttribute("config", config);
        List<ConnectionInternetType> connectionInternetTypes = connectionInternetTypeService.getAllConnectionInternetTypes();
        model.addAttribute("connectionInternetTypes", connectionInternetTypes);
        return "redirect:/config/" + id;
    }
}
