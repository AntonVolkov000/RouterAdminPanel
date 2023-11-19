package application.services;

import application.model.ConnectionDeviceType;
import application.model.Device;
import application.model.GeneralConfig;
import application.repository.ConnectionDeviceTypeRepository;
import application.repository.DeviceRepository;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.IPv4Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Random;

import static net.andreinc.mockneat.types.enums.IPv4Type.*;

@Component
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final ConnectionDeviceTypeRepository connectionDeviceTypeRepository;
    private static final String[] phoneBrands = {"Apple", "Samsung", "Google", "Huawei", "Xiaomi"};
    private static final String[] computerBrands = {"Dell", "Lenovo", "HP", "Asus", "Acer"};
    private static final String[] phoneModels = {"X", "11", "S10", "Pixel", "P30"};
    private static final String[] computerModels = {"Inspiron", "ThinkPad", "Pavilion", "ZenBook", "Predator"};

    @Autowired
    public DeviceService(
            DeviceRepository deviceRepository,
            ConnectionDeviceTypeRepository connectionDeviceTypeRepository
    ) {
        this.deviceRepository = deviceRepository;
        this.connectionDeviceTypeRepository = connectionDeviceTypeRepository;
    }

    public void deleteDevice(Long id) {
        Device device = deviceRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid device Id:" + id));
        deviceRepository.delete(device);
    }

    public void generateDevice(GeneralConfig generalConfig) {
        Random random = new Random();
        Boolean randBoolean = random.nextBoolean();
        Device device = new Device();

        device.setDeviceName(generateDeviceName(randBoolean));
        device.setConnectionDeviceType(generateConnectionDeviceType(randBoolean));
        device.setGeneralConfig(generalConfig);
        IPv4Type type = CLASS_B;
        device.setIpAddress(generateIpAddress(type));
        device.setMacAddress(generateMacAddress());

        deviceRepository.save(device);
    }

    private String generateDeviceName(Boolean randBoolean) {
        return randBoolean ? generatePhoneName() : generateComputerName();
    }

    private String generatePhoneName() {
        Random random = new Random();
        String brand = phoneBrands[random.nextInt(phoneBrands.length)];
        String model = phoneModels[random.nextInt(phoneModels.length)];
        String randInt = String.valueOf(random.nextInt(20));
        return brand + "-" + model + "-" + randInt;
    }

    private String generateComputerName() {
        Random random = new Random();
        String brand = computerBrands[random.nextInt(computerBrands.length)];
        String model = computerModels[random.nextInt(computerModels.length)];
        String randInt = String.valueOf(random.nextInt(20));
        return brand + "-" + model + "-" + randInt;
    }

    private ConnectionDeviceType generateConnectionDeviceType(Boolean randBoolean) {
        Long ConnectionDeviceTypeId = randBoolean ? 2L : 1L;
        ConnectionDeviceType connectionDeviceType = connectionDeviceTypeRepository
                .findById(ConnectionDeviceTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid connectionDeviceType Id:" + ConnectionDeviceTypeId));
        return connectionDeviceType;
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

    private String generateMacAddress() {
        Random random = new Random();
        byte[] macAddress = new byte[6];
        random.nextBytes(macAddress);
        StringBuilder sb = new StringBuilder(18);
        String separator = "-";
        for (byte b : macAddress) {
            if (sb.length() > 0) {
                sb.append(separator);
            }
            sb.append(String.format("%02X", b));
        }
        return sb.toString().toUpperCase();
    }
}
