package application.model;

import application.configuration.SecurityConfig;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "admin_account")
public class AdminAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "admin_account_id")
    private long adminAccountId;

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
            fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    // Отношение таблицы "Recipient" к таблице "AdminAccount" многие ко многим.
    // Администратор может иметь много получателей писем в списке для оповещения.
    // Аналогично один получатель может быть в списке для оповещения у нескольких администраторов
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinTable(name = "adminAccount_recipient",
            joinColumns = @JoinColumn(name = "admin_account_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"admin_account_id", "recipient_id"}))
    private Set<Recipient> recipients;

    @OneToOne (mappedBy="adminAccount")
    private GeneralConfig generalConfig;

    public long getAdminAccountId() {
        return adminAccountId;
    }

    public void setAdminAccountId(long adminAccountId) {
        this.adminAccountId = adminAccountId;
    }
    public void setLogin(String newLogin) {
        this.login = newLogin;
    }

    public void setPassword(String password) {
        this.password = new SecurityConfig().encodePassword(password);
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
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

    public GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    public void setGeneralConfig(GeneralConfig generalConfig) {
        this.generalConfig = generalConfig;
    }

    public Set<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(Set<Recipient> recipients) {
        this.recipients = recipients;
    }

    public AdminAccount()
    {

    }
}
