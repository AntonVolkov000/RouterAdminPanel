package application.services;

import application.model.Account;
import application.repository.AccountRepository;
import application.repository.AdminAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class AccountService {
    private final AccountRepository accountRepository;
    @Autowired
    public AccountService( AccountRepository accountRepository)
    { this.accountRepository = accountRepository;
    }

    public Account generateNewAccount()
    {
        Account newAccount = new Account(generateAccountNumber(), generateSum());
        accountRepository.save(newAccount);
        return newAccount;
    }

    public String generateAccountNumber() {
        long start = 999_999_999L; // 10 цифр
        long end = 10_000_000_000L;
        return String.valueOf(ThreadLocalRandom.current().nextLong(start, end));
    }

    public double generateSum() {
        double start = 0.0;
        double end = 1_000_000.0;
        Random generator =  new Random();
        double randomDouble = generator.nextDouble() * (end - start);
        BigDecimal bd = new BigDecimal(
                randomDouble)
                .setScale(2, RoundingMode.HALF_DOWN);
        // Режим округления для округления в сторону
        // "ближайшего соседа", если только оба соседа
        // не находятся на равном расстоянии,
        // и в этом случае округление в меньшую сторону.
        return bd.doubleValue();
    }
}
