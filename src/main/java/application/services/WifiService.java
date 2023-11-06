package application.services;

import application.repository.WifiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WifiService {
    private final WifiRepository wifiRepository;
    @Autowired
    public WifiService(WifiRepository wifiRepository)
    {
        this.wifiRepository = wifiRepository;
    }
}
