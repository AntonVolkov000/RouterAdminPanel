package application.services;

import application.model.WifiMode;
import application.repository.WifiModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WifiModeService {
    private final WifiModeRepository wifiModeRepository;
    @Autowired
    public WifiModeService(WifiModeRepository wifiModeRepository)
    {
        this.wifiModeRepository = wifiModeRepository;
    }

    public List<WifiMode> getAllWifiModes() {
        return wifiModeRepository.findAll();
    }
    public WifiMode findByModeName(String modeName) {
        return wifiModeRepository.findByModeName(modeName);
    }
}
