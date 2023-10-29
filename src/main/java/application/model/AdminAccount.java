package application.model;

import application.configuration.SecurityConfig;
import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@Entity
@Table(name = "admin_account")
public class AdminAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name="active")
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();


    // Отношение таблицы "AdminAccount" к таблице "Account" один к одному.
    // Администратор может иметь только один лицевой счет
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    // Отношение таблицы "TelegramUsers" к таблице "AdminAccount" многие ко многим.
    // Администратор может иметь много телеграм пользователей в списке для оповещения.
    // Телеграм пользователь может быть в списке для оповещения у нескольких администраторов
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(name = "adminAccount_telegramUser",
            joinColumns = @JoinColumn(name = "telegram_user_id"),
            inverseJoinColumns = @JoinColumn(name = "admin_account_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"telegram_user_id", "admin_account_id"}))
    private Set<TelegramUser> telegramUsers = new HashSet<>();

    public void setLogin(String newLogin) {
        this.login = newLogin;
    }

    public void setPassword(String password) {
        this.password = new SecurityConfig().encodePassword(password);
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public long getId() {
        return this.id;
    }

    public Account getAccount ()
    {
        return this.account;
    }

    public void setAccount (Account account)
    {
        this.account = account;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AdminAccount()
    {

    }

    public AdminAccount (String login, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.login = login;
        this.password = password;
//        this.setPassword(password);
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
}
