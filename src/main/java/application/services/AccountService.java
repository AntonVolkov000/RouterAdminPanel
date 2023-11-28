package application.services;

import application.model.Account;
import application.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountService {
    private final AccountRepository accountRepository;
    @Autowired
    public AccountService( AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }

    public Account getAccountById(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account mapped with " +
                                                                "adminAccount, id:" + id));
    }
}
