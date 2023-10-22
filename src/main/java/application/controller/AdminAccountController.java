package application.controller;

import application.model.Account;
import application.model.AdminAccount;
import application.repository.AccountRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import application.repository.AdminAccountRepository;


import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Controller
public class AdminAccountController {
    private final AdminAccountRepository adminAccountRepository;
    private final AccountRepository accountRepository;
    @Autowired
    public AdminAccountController(AdminAccountRepository adminAccountRepository, AccountRepository accountRepository) {
        this.adminAccountRepository = adminAccountRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/admins")
    public ResponseEntity<List<AdminAccount>> getAllAdminAccounts(@RequestParam(required = false) String login)
    {
        System.out.println("in admins");
        try{
            List<AdminAccount> adminAccounts = new ArrayList<>();
            if (login == null)
                adminAccounts.addAll(adminAccountRepository.findAll());
            else
                adminAccounts.addAll(adminAccountRepository.findByLogin(login));

            System.out.println(adminAccounts.size());
            if (adminAccounts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            System.out.println("ok");
            return new ResponseEntity<>(adminAccounts, HttpStatus.OK);

        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/new")
    public String showSignUpForm(AdminAccount adminAccount) {
        return "add-adminAccount";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        AdminAccount adminAccount = adminAccountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin_account Id:" + id));
        model.addAttribute("adminAccount", adminAccount);
        return "update-adminAccount";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        AdminAccount adminAccount = adminAccountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        adminAccountRepository.delete(adminAccount);
        model.addAttribute("adminAccounts", adminAccountRepository.findAll());
        System.out.println("in delete");
        System.out.println(adminAccountRepository.findAll().size());
        return "index";
    }
    @PostMapping("/addAdminAccount")
    public String addUser(@Valid AdminAccount adminAccount, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-adminAccount";
        }
        Account newAccount = new Account(Account.generateAccountNumber(), Account.generateSum());
        accountRepository.save(newAccount);

        adminAccount.setAccount(newAccount);
        adminAccountRepository.save(adminAccount);
        model.addAttribute("adminAccounts", adminAccountRepository.findAll());
        return "index";
    }
    @PostMapping("/update/{id}")
    public String updateUser(
            @PathVariable("id") long id, @Valid AdminAccount adminAccount, BindingResult result, Model model) {
        if (result.hasErrors()) {
            adminAccount.setId(id);
            return "update-adminAccount";
        }
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account mapped with adminAccount, id:" + id));
        if (isNull(adminAccount.getAccount()))
        {
            adminAccount.setAccount(account);
        }
        adminAccountRepository.save(adminAccount);
        model.addAttribute("adminAccounts", adminAccountRepository.findAll());
        return "index";
    }
}
