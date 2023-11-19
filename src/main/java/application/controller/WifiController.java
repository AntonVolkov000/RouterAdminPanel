package application.controller;

import application.model.*;
import application.services.WifiModeService;
import application.services.WifiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/wifi")
public class WifiController {
    private final WifiService wifiService;
    private final WifiModeService wifiModeService;

    @Autowired
    public WifiController(WifiService wifiService, WifiModeService wifiModeService) {
        this.wifiService = wifiService;
        this.wifiModeService = wifiModeService;
    }

    @GetMapping("/{id}")
    public String wifiById(@PathVariable("id") long id, Model model) {
        Wifi wifi = wifiService.prepapreWifi(id);
        List<WifiMode> wifiModes = wifiModeService.getAllWifiModes();
        model.addAttribute("wifi", wifi);
        model.addAttribute("wifiModes", wifiModes);
        return "wifi";
    }

    @PostMapping("/change/{id}")
    public String changeWifi(@PathVariable("id") long id,
                             String ssid, String password, String wifiMode,
                             Model model) {
        WifiMode wifiModeEntity = wifiModeService.findByModeName(wifiMode);
        Wifi wifi = wifiService.changeWifi(id, ssid, password, wifiModeEntity);

        List<WifiMode> wifiModes = wifiModeService.getAllWifiModes();
        model.addAttribute("wifi", wifi);
        model.addAttribute("wifiModes", wifiModes);
        return "wifi";
    }
}
