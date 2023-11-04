package application;

import application.services.AccountService;
import application.services.AdminAccountService;
import application.services.TelegramUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.github.cdimascio.dotenv.Dotenv;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@SpringBootApplication
public class RouterAdminPanelApplication {
    public static void main(String[] args) {
        SpringApplication.run(RouterAdminPanelApplication.class, args);
    }

    @Bean
    public boolean loadData(AccountService accountService,
                                      TelegramUserService telegramUserService,
                                      AdminAccountService adminAccountService) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Dotenv dotenv = Dotenv.load();
        String initDB = dotenv.get("INIT_DB");
        if (initDB.equals("true")) {
            accountService.loadData();
            telegramUserService.loadData();
            adminAccountService.loadData(
                    accountService.getAllAccounts().get(0),
                    List.of(telegramUserService.getTelegramUserByTelegramUserName("test1")));
            return true;
        }
        return false;
    }
}
