package application.services;

import application.model.Wifi;
import application.model.WifiMode;
import application.repository.WifiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class WifiService {
    private final WifiRepository wifiRepository;
    @Autowired
    public WifiService(WifiRepository wifiRepository)
    {
        this.wifiRepository = wifiRepository;
    }
    public Wifi getWifiById(Long id) {
        return wifiRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
    }

    public Wifi prepapreWifi(Long id) {
        Wifi wifi = getWifiById(id);
        final int passwordLength = 10;
        boolean wasModified = false;
        if (wifi.getSsid() == null){
            String ssid = generateSsid();
            wifi.setSsid(ssid);
            wasModified = true;
        }
        if (wifi.getPassword() == null){
            String password = generatePassword(passwordLength);
            wifi.setPassword(password);
            wasModified = true;
        }
        if (wasModified)
            wifiRepository.save(wifi);
        return wifi;
    }

    public String generateSsid() {
        String routerBrand = "AA95_R-";
        int start = 1000;
        int end = 9999;
        String lastFourDigits = String.valueOf(ThreadLocalRandom.current().nextInt(start, end));
        return routerBrand.concat(lastFourDigits);
    }

    public static String generatePassword (int passwordLen)
    {
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";

        String values = Capital_chars + Small_chars + numbers + symbols;

        Random randomGenerator = new Random();
        char[] password = new char[passwordLen];

        for (int i = 0; i < passwordLen; i++)
        {
            password[i] = values.charAt(
                    randomGenerator.nextInt(values.length())
            );
        }
        return String.valueOf(password);
    }

    public Wifi changeWifi(Long id, String ssid, String password, WifiMode wifiMode) {
        Wifi wifi = getWifiById(id);
        boolean isSsidUnique = checkSsidUniqueness(ssid);
        if (isSsidUnique)
            wifi.setSsid(ssid);
        else if (wifi.getSsid() == null ) {
            String uniqueSsid = generateSsid();
            wifi.setSsid(uniqueSsid);
        }
        wifi.setPassword(password);
        wifi.setWifiMode(wifiMode);
        wifiRepository.save(wifi);
        return wifi;
    }

    public boolean checkSsidUniqueness(String ssid) {
        boolean unique = true;
        List<Wifi> wifiList = getAllWifi();
        for (Wifi wifi: wifiList) {
            if (wifi.getSsid().equals(ssid)) {
                unique = false;
                break;
            }
        }
        return unique;
    }

    public List<Wifi> getAllWifi() {
        return wifiRepository.findAll();
    }
}
