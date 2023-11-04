package application.controller;

import application.model.Account;
import application.model.AdminAccount;
import application.services.AccountService;
import application.services.AdminAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/adminAccount")
public class AdminAccountController {
    private final AdminAccountService adminAccountService;
    private final AccountService accountService;
    @Autowired
    public AdminAccountController(AdminAccountService adminAccountService, AccountService accountService) {
        this.adminAccountService = adminAccountService;
        this.accountService = accountService;
    }

    @GetMapping("/admins")
    public ResponseEntity<List<AdminAccount>> getAllAdminAccounts(@RequestParam(required = false) String login)
    {
        try{
            List<AdminAccount> adminAccounts = new ArrayList<>();
            if (login == null) {
                adminAccounts.addAll(adminAccountService.getAllAdminAccounts());
            }
            else {
                AdminAccount adminAccount = adminAccountService.getAdminAccountByLogin(login);
                adminAccounts.add(adminAccount);
            }

            if (adminAccounts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(adminAccounts, HttpStatus.OK);

        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/edit/{id}")
    public String editAdminAccount(@PathVariable("id") long id, Model model) {
        AdminAccount adminAccount = adminAccountService.editAdminAccount(id);
        model.addAttribute("adminAccount", adminAccount);
        return "update-adminAccount";
    }
    @GetMapping("/delete/{id}")
    public String deleteAdminAccount(@PathVariable("id") long id, Model model) {
        adminAccountService.deleteAdminAccount(id);
        return "registration";
    }

    @PostMapping("/update/{id}")
    public String updateAdminAccount(@PathVariable("id") long id, @Valid AdminAccount adminAccount,
                                     Model model) {
        Account account = accountService.getAccountById(id);
        adminAccount.setAdminAccountId(id);
        adminAccountService.updateAdminAccount(adminAccount, account);
        model.addAttribute("adminAccount", adminAccount);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String adminAccount(AdminAccount adminAccount, Model model) {
        model.addAttribute("adminAccount", adminAccount);
        return "main-adminAccount";
    }

    @GetMapping("/{id}")
    public String adminAccountById(@PathVariable("id") long id, Model model) {
        AdminAccount adminAccount = adminAccountService.getAdminAccountById(id);
        model.addAttribute("adminAccount", adminAccount);
        return "main-adminAccount";
    }
}
