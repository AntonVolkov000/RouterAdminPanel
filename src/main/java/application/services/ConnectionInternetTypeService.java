package application.services;

import application.model.AdminAccount;
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

    public void loadData(){
        connectionInternetTypeRepository.save(new ConnectionInternetType(
                1, "Статический IP-адрес"
        ));
        connectionInternetTypeRepository.save(new ConnectionInternetType(
                2, "Динамический IP-адрес"
        ));
    }
}
