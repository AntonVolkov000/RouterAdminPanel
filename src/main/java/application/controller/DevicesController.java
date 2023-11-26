package application.controller;

import application.model.Device;
import application.model.GeneralConfig;
import application.services.DeviceService;
import application.services.GeneralConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/devices")
public class DevicesController {
    private final GeneralConfigService generalConfigService;
    private final DeviceService deviceService;
    @Autowired
    public DevicesController(
        GeneralConfigService generalConfigService,
        DeviceService deviceService
    ) {
        this.generalConfigService = generalConfigService;
        this.deviceService = deviceService;
    }

    @GetMapping("/{generalConfigId}")
    public String devices(@PathVariable("generalConfigId") long generalConfigId, Model model) {
        GeneralConfig generalConfig = generalConfigService.getGeneralConfigById(generalConfigId);
        List<Device> devices = generalConfig.getDevices();
        model.addAttribute("devices", devices);
        return "devices";
    }

    @GetMapping("{generalConfigId}/delete/{id}")
    public String deleteDevice(@PathVariable("generalConfigId") long generalConfigId, @PathVariable("id") long id, Model model) {
        deviceService.deleteDevice(id);
        GeneralConfig generalConfig = generalConfigService.getGeneralConfigById(generalConfigId);
        List<Device> devices = generalConfig.getDevices();
        model.addAttribute("devices", devices);
        return "devices";
    }

    @GetMapping("{generalConfigId}/generate")
    public String generateDevice(@PathVariable("generalConfigId") long generalConfigId, Model model) {
        GeneralConfig generalConfig = generalConfigService.getGeneralConfigById(generalConfigId);
        deviceService.generateDevice(generalConfig);
        List<Device> devices = generalConfig.getDevices();
        model.addAttribute("devices", devices);
        return "devices";
    }
}
