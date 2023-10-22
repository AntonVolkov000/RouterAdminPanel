package application.controller;

import application.model.TelegramUser;
import application.repository.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tgUsers")
public class TelegramUserController {
    private final TelegramUserRepository telegramUserRepository;
    @Autowired
    public TelegramUserController (TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    @GetMapping
    public List<TelegramUser> getAllTelegramUsers () {
        return telegramUserRepository.findAll();
    }

    @PostMapping
    public TelegramUser createTelegramUser (@RequestBody TelegramUser telegramUser) {
        return telegramUserRepository.save(telegramUser);
    }

    @GetMapping("/{id}")
    public TelegramUser getUserByTelegramId(@PathVariable("id") Long id) {
        return telegramUserRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Telegram User Id: " + id));
    }
}
