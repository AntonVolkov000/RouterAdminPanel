package application.services;

import application.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceService {
    private final DeviceRepository deviceRepository;
    @Autowired
    public DeviceService(DeviceRepository deviceRepository)
    {
        this.deviceRepository = deviceRepository;
    }
}
