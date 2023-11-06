package application;

import application.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@SpringBootApplication
public class RouterAdminPanelApplication {
    @Value("${INIT_DB}")
    Boolean initBD;

    public static void main(String[] args) {
        SpringApplication.run(RouterAdminPanelApplication.class, args);
    }

    @Bean
    public boolean loadData(
            AccountService accountService,
            TelegramUserService telegramUserService,
            AdminAccountService adminAccountService,
            ConnectionInternetTypeService connectionInternetTypeService,
            ConfigService configService,
            WifiModeService wifiModeService,
            WifiService wifiService,
            ConnectionDeviceTypeService connectionDeviceTypeService,
            DeviceService deviceService,
            GeneralConfigService generalConfigService
            ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (initBD) {
            accountService.loadData();
            telegramUserService.loadData();
            adminAccountService.loadData(
                    accountService.getAllAccounts().get(0),
                    List.of(telegramUserService.getTelegramUserByTelegramUserName("test1")));

//            connectionInternetTypeService.loadData();
//            configService.loadData();

//            wifiModeService.loadData();
//            wifiService.loadData();
//
//            ConnectionDeviceTypeService.loadData();
//            DeviceService.loadData();

//            generalConfigService.loadData();
            return true;
        }
        return false;
    }
}
