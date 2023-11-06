package application.services;

import application.repository.WifiModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WifiModeService {
    private final WifiModeRepository wifiModeRepository;
    @Autowired
    public WifiModeService(WifiModeRepository wifiModeRepository)
    {
        this.wifiModeRepository = wifiModeRepository;
    }
}
