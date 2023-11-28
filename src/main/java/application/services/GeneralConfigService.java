package application.services;

import application.model.GeneralConfig;
import application.repository.GeneralConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneralConfigService {
    private final GeneralConfigRepository generalConfigRepository;

    @Autowired
    public GeneralConfigService(
            GeneralConfigRepository generalConfigRepository
    )
    {
        this.generalConfigRepository = generalConfigRepository;
    }

    public GeneralConfig getGeneralConfigById(Long id) {
        return generalConfigRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid generalConfig Id:" + id));
    }

    public GeneralConfig getGeneralConfigByAdminLogin (String adminAccountLogin) {
        return generalConfigRepository.findByAdminAccountLogin(adminAccountLogin);
    }
}
