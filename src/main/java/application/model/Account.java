package application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private long accountId;
    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "sum")
    private double sum;

    @OneToOne(mappedBy = "account")
    private AdminAccount adminAccount;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber()
    {
        return this.accountNumber;
    }
    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getSum() {
        return this.sum;
    }

    public AdminAccount getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(AdminAccount adminAccount) {
        this.adminAccount = adminAccount;
    }

    public Account() {}
}
