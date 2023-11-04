package application.model;

import jakarta.persistence.*;
import java.util.Random;

import java.util.List;

@Entity
@Table(name = "telegram_user")
public class TelegramUser {
    @Id
    @Column(name = "telegram_user_id")
    private long telegramUserId;

    @Column(name = "telegram_user_name")
    private String telegramUserName;

    // Отношение таблицы "TelegramUsers" к таблице "AdminAccount" многие ко многим.
    // Администратор может иметь много телеграм пользователей в списке для оповещения.
    // Телеграм пользователь может быть в списке для оповещения у нескольких администраторов
    @ManyToMany(mappedBy = "telegramUsers")
    private List<AdminAccount> adminAccounts;

    public void setTelegramUserId (long id)
    {
        this.telegramUserId = id;
    }

    public long getTelegramUserId ()
    {
        return this.telegramUserId;
    }

    public void setTelegramUserName (String telegramUserName)
    {
        this.telegramUserName = telegramUserName;
    }

    public String getTelegramUserName ()
    {
        return this.telegramUserName;
    }

    public List<AdminAccount> getAdminAccounts() {
        return adminAccounts;
    }

    public void setAdminAccounts(List<AdminAccount> adminAccounts) {
        this.adminAccounts = adminAccounts;
    }

    public TelegramUser(long id, String telegramUserName)
    {
        this.telegramUserId = id;
        this.telegramUserName = telegramUserName;
    }

    public TelegramUser()
    {

    }

    public static long generateTelegramUserId()
    {
        long start = 99_999_999; // 9 цифр
        long end = 1_000_000_000L;
        Random generator =  new Random(start);
        return generator.nextLong(end);
    }
}
