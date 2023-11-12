package application.services;

import application.model.ConnectionInternetType;
import application.repository.ConnectionInternetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConnectionInternetTypeService {
    private final ConnectionInternetTypeRepository connectionInternetTypeRepository;
    @Autowired
    public ConnectionInternetTypeService(ConnectionInternetTypeRepository connectionInternetTypeRepository)
    {
        this.connectionInternetTypeRepository = connectionInternetTypeRepository;
    }

    public List<ConnectionInternetType> getAllConnectionInternetTypes() {
        return connectionInternetTypeRepository.findAll();
    }
}
