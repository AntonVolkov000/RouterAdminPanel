package application.services;

import application.model.Account;
import application.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class AccountService {
    private final AccountRepository accountRepository;
    @Autowired
    public AccountService( AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }

    public Account generateNewAccount()
    {
        Account newAccount = new Account(generateAccountNumber(), generateSum());
        accountRepository.save(newAccount);
        return newAccount;
    }

    public Account getAccountById(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account mapped with " +
                                                                "adminAccount, id:" + id));
    }

    public void deleteAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        accountRepository.delete(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.getAccountByAccountNumber(accountNumber).get(0);
    }

    public double getSumByAccountNumber(String accountNumber) {
        return getAccountByNumber(accountNumber).getSum();
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

    public void loadData() {
        System.out.println("in load data");
        for (int i = 0; i < 3; i++)
        {
            accountRepository.save(new Account(generateAccountNumber(), generateSum()));
        }
//            repo.save(new TelegramUser(TelegramUser.generateTelegramUserId(), "test1"));
//            repo.save(new TelegramUser(454654725, "vapVelichko"));
    }
}
