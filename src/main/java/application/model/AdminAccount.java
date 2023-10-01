package application.model;

import jakarta.persistence.*;

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

    public void setLogin(String newLogin) {
        this.login = newLogin;
    }

    public void setPassword (String password) {
        // Не хранить пароль в открытом виде. Использовать хэширование пароля.
        this.password = password;
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
}
