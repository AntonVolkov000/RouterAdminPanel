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

    public static String generateAccountNumber() {
        long start = 999_999_999L; // 10 цифр
        long end = 10_000_000_000L;
        return String.valueOf(ThreadLocalRandom.current().nextLong(start, end));
    }

    public static double generateSum() {
        double start = 0.0;
        double end = 1_000_000.0;
        Random generator =  new Random();
        double randomDouble = generator.nextDouble() * (end - start);
        BigDecimal bd = new BigDecimal(
                randomDouble)
                .setScale(2, RoundingMode.HALF_DOWN);// Режим округления для округления в сторону
                                                             // "ближайшего соседа", если только оба соседа
                                                             // не находятся на равном расстоянии,
                                                             // и в этом случае округление в меньшую сторону.
        return bd.doubleValue();
    }
}
