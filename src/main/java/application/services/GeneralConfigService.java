package application.services;

import application.model.*;
import application.repository.ConfigRepository;
import application.repository.GeneralConfigRepository;
import application.repository.WifiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneralConfigService {
    private final GeneralConfigRepository generalConfigRepository;
    private final ConfigRepository configRepository;
    private final WifiRepository wifiRepository;

    @Autowired
    public GeneralConfigService(
            GeneralConfigRepository generalConfigRepository,
            ConfigRepository configRepository,
            WifiRepository wifiRepository
    )
    {
        this.generalConfigRepository = generalConfigRepository;
        this.configRepository = configRepository;
        this.wifiRepository = wifiRepository;
    }

    public GeneralConfig getGeneralConfigById(Long id) {
        return generalConfigRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid generalConfig Id:" + id));
    }

    public void loadData(){
        Config config = configRepository
                .findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Invalid config Id:" + 1));
//        Wifi wifi = wifiRepository
//                .findById(1L)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid wifi Id:" + 1));
        GeneralConfig generalConfig = new GeneralConfig(
                1, null, config, null, null
        );
        generalConfigRepository.save(generalConfig);
    }
}
