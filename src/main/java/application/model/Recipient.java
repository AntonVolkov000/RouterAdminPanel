package application.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "recipient")
public class Recipient {
    @Id
    @Column(name = "recipient_id")
    private long recipientId;

    // Отношение таблицы "Recipient" к таблице "AdminAccount" многие ко многим.
    // Администратор может иметь много получателей писем в списке для оповещения.
    // Аналогично один получатель может быть в списке для оповещения у нескольких администраторов
    @ManyToMany(mappedBy = "recipients")
    private Set<AdminAccount> adminAccounts;

    @Column(name = "email", unique=true)
    private String email;

    public Recipient()
    {

    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public Set<AdminAccount> getAdminAccounts() {
        return adminAccounts;
    }

    public void setAdminAccounts(Set<AdminAccount> adminAccounts) {
        this.adminAccounts = adminAccounts;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
