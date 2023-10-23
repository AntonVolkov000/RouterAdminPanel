package application;

import application.model.Account;
import application.model.AdminAccount;
import application.model.TelegramUser;
import application.repository.AccountRepository;
import application.repository.AdminAccountRepository;
import application.repository.TelegramUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@SpringBootApplication
public class RouterAdminPanelApplication {
    public static void main(String[] args) {
        SpringApplication.run(RouterAdminPanelApplication.class, args);
    }

    @Bean
    public CommandLineRunner telegramUsersDataLoader(TelegramUserRepository repo) {

        return args -> {
            repo.save(new TelegramUser(TelegramUser.generateTelegramUserId(), "test1"));
            repo.save(new TelegramUser(454654725, "vapVelichko"));
        };
    }

//    @Bean
//    public CommandLineRunner accountDataLoader (AccountRepository repo) {
//
//        return args -> {
//            repo.save(new Account(Account.generateAccountNumber(), Account.generateSum()));
//            repo.save(new Account(Account.generateAccountNumber(), Account.generateSum()));
//        };
//    }
}
