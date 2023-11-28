package application.controller;

import application.model.Account;
import application.model.AdminAccount;
import application.services.AccountService;
import application.services.AdminAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin-account")
public class AdminAccountController {
    private final AdminAccountService adminAccountService;
    private final AccountService accountService;
    @Autowired
    public AdminAccountController(AdminAccountService adminAccountService, AccountService accountService) {
        this.adminAccountService = adminAccountService;
        this.accountService = accountService;
    }

    @GetMapping("/edit/{id}")
    public String editAdminAccount(@PathVariable("id") long id, Model model) {
        AdminAccount adminAccount = adminAccountService.editAdminAccount(id);
        model.addAttribute("adminAccount", adminAccount);
        return "admin-account-update";
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
        return "admin-account";
    }

    @GetMapping("/{id}")
    public String adminAccountById(@PathVariable("id") long id, Model model) {
        AdminAccount adminAccount = adminAccountService.getAdminAccountById(id);
        model.addAttribute("adminAccount", adminAccount);
        return "admin-account";
    }
}
