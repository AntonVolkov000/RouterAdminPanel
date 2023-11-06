package application.services;

import application.model.WifiMode;
import application.repository.ConnectionDeviceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionDeviceTypeService {
    private final ConnectionDeviceTypeRepository connectionDeviceTypeRepository;
    @Autowired
    public ConnectionDeviceTypeService(ConnectionDeviceTypeRepository connectionDeviceTypeRepository)
    {
        this.connectionDeviceTypeRepository = connectionDeviceTypeRepository;
    }
}
