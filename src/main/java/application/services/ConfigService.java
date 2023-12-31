package application.services;

import application.model.Config;
import application.model.ConnectionInternetType;
import application.repository.ConfigRepository;
import application.repository.ConnectionInternetTypeRepository;
import io.micrometer.common.util.StringUtils;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.IPv4Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static net.andreinc.mockneat.types.enums.IPv4Type.*;

@Component
public class ConfigService {
    private final ConfigRepository configRepository;
    private final ConnectionInternetTypeRepository connectionInternetTypeRepository;

    @Autowired
    public ConfigService(
            ConfigRepository configRepository,
            ConnectionInternetTypeRepository connectionInternetTypeRepository
    ) {
        this.configRepository = configRepository;
        this.connectionInternetTypeRepository = connectionInternetTypeRepository;
    }

    public Config getConfigById(Long id) {
        return configRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
    }

    public Boolean isStaticConfigById(Long id) {
        ConnectionInternetType connectionInternetType = configRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id))
                .getConnectionInternetType();
        if (connectionInternetType != null) {
            return connectionInternetType.getConnectionInternetName().equals("Статический IP-адрес");
        }
        return false;
    }

    private String generateIpAddress(IPv4Type type) {
        MockNeat mock = MockNeat.threadLocal();
        String ip;
        switch (type) {
            case CLASS_A -> ip = mock.ipv4s().type(CLASS_A).val();
            case CLASS_B -> ip = mock.ipv4s().type(CLASS_B).val();
            case CLASS_C -> ip = mock.ipv4s().type(CLASS_C).val();
            case CLASS_D -> ip = mock.ipv4s().type(CLASS_D).val();
            case CLASS_E -> ip = mock.ipv4s().type(CLASS_E).val();
            default -> ip = mock.ipv4s().val();
        }
        return ip;
    }

    private String generateMask(IPv4Type type) {
        String mask;
        switch (type) {
            case CLASS_A -> mask = "255.0.0.0";
            case CLASS_B -> mask = "255.255.0.0";
            case CLASS_C -> mask = "255.255.255.0";
            default -> mask = "255.255.255.255";
        }
        return mask;
    }

    private String generateGate(String ip) {
        String[] gateAddressArray = ip.split("\\.");
        String zero = "0";
        String unit = "1";
        gateAddressArray[2] = zero;
        gateAddressArray[3] = unit;
        String gate = String.join(".", gateAddressArray);
        if (gate.equals(ip)) {
            gateAddressArray[2] = unit;
            return String.join(".", gateAddressArray);
        }
        return gate;
    }

    public Config prepareConfig(Long config_id) {
        Config config = getConfigById(config_id);
        boolean wasModified = false;
        IPv4Type type = CLASS_B;
        if (StringUtils.isEmpty(config.getIp())) {
            String ip = generateIpAddress(type);
            config.setIp(ip);
            wasModified = true;
        }
        if (StringUtils.isEmpty(config.getMask())) {
            String mask = generateMask(type);
            config.setMask(mask);
            wasModified = true;
        }
        if (StringUtils.isEmpty(config.getGate())) {
            String gate = generateGate(config.getIp());
            config.setGate(gate);
            wasModified = true;
        }
        if (wasModified)
            configRepository.save(config);
        return config;
    }

    public Config updateConfig(Long configId) {
        Config config = getConfigById(configId);
        IPv4Type type = CLASS_B;
        String ip = generateIpAddress(type);
        config.setIp(ip);
        config.setMask(generateMask(type));
        config.setGate(generateGate(ip));
        configRepository.save(config);
        return config;
    }

    public Config changeConfig(long configId, String ip, String mask, String gate) {
        Config config = getConfigById(configId);
        config.setIp(ip);
        config.setMask(mask);
        config.setGate(gate);
        configRepository.save(config);
        return config;
    }

    public Config updateConnectionInternetType(long configId, long connectionInternetTypeId) {
        Config config = getConfigById(configId);
        ConnectionInternetType connectionInternetType = connectionInternetTypeRepository
                .findById(connectionInternetTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid connectionInternetType Id:" + connectionInternetTypeId));
        config.setConnectionInternetType(connectionInternetType);
        configRepository.save(config);
        return config;
    }
}