package application.controller;

import application.model.TelegramUser;
import application.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tgUsers")
public class TelegramUserController {
    private final TelegramUserService telegramUserService;
    @Autowired
    public TelegramUserController (TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    @GetMapping("/all")
    public List<TelegramUser> getAllTelegramUsers () {
        return telegramUserService.getAllTelegramUsers();
    }

    @GetMapping("/{id}")
    public TelegramUser getUserByTelegramId(@PathVariable("id") Long id) {
        return telegramUserService.getUserByTelegramId(id);
    }
}
