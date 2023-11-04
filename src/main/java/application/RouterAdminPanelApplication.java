package application;

import application.model.TelegramUser;
import application.repository.TelegramUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RouterAdminPanelApplication {
    public static void main(String[] args) {
        SpringApplication.run(RouterAdminPanelApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner telegramUsersDataLoader(TelegramUserRepository repo) {
//
//        return args -> {
//            repo.save(new TelegramUser(TelegramUser.generateTelegramUserId(), "test1"));
//            repo.save(new TelegramUser(454654725, "vapVelichko"));
//        };
//    }
}
