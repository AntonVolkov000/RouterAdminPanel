package application.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "sum")
    private double sum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Account(String accountNumber, double sum) {
        this.accountNumber = accountNumber;
        this.sum = sum;
    }

    public Account() {}
}
