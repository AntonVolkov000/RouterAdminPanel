package application.controller;

import application.model.Account;
import application.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    @Autowired
    public AccountController ( AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public List<Account> getAllAccounts () {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountNumber}")
    public Account getAccountByNumber(@PathVariable("accountNumber") String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }
    @GetMapping("/sum/{accountNumber}")
    public double getSumByNumber(@PathVariable("accountNumber") String accountNumber) {
        return accountService.getSumByAccountNumber(accountNumber);
    }
}
