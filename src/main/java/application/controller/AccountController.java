package application.controller;

import application.model.Account;
import application.model.TelegramUser;
import application.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountRepository accountRepository;
    @Autowired
    public AccountController (AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public List<Account> getAllAccounts () {
        return accountRepository.findAll();
    }

    @PostMapping
    public Account createAccount (@RequestBody Account account) {
        return accountRepository.save(account);
    }

    @GetMapping("/{accountNumber}")
    public Account getAccountByNumber(@PathVariable("accountNumber") String accountNumber) {
        return accountRepository
                .getAccountByAccountNumber(accountNumber).get(0);
    }
    @GetMapping("/sum/{accountNumber}")
    public double getSumByNumber(@PathVariable("accountNumber") String accountNumber) {
        return accountRepository
                .getAccountByAccountNumber(accountNumber)
                .get(0)
                .getSum();
    }
}
