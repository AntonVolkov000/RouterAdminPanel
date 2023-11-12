package application.services;

import application.model.TelegramUser;
import application.repository.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;
    @Autowired
    public TelegramUserService( TelegramUserRepository telegramUserRepository)
    {
        this.telegramUserRepository = telegramUserRepository;
    }

    public List<TelegramUser> getAllTelegramUsers () {
        return telegramUserRepository.findAll();
    }

    public TelegramUser getUserByTelegramId(Long id) {
        return telegramUserRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Telegram User Id: " + id));
    }
    public TelegramUser getTelegramUserByTelegramUserName(String telegramUserName) {
        return telegramUserRepository.findByTelegramUserName(telegramUserName);
    }

    public static long generateTelegramUserId()
    {
        long start = 99_999_999; // 9 цифр
        long end = 1_000_000_000L;
        return ThreadLocalRandom.current().nextLong(start, end);
    }
}
