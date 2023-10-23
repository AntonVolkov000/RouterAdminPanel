package application.controller;

import application.model.Account;
import application.model.AdminAccount;

import application.services.AccountService;
import application.services.AdminAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final AdminAccountService adminAccountService;
    private final AccountService accountService;
    @Autowired
    public RegistrationController(AdminAccountService adminAccountService, AccountService accountService) {
        this.adminAccountService = adminAccountService;
        this.accountService = accountService;
    }

    @GetMapping("/registration")
    public String registration()
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String addAdminAccount(AdminAccount adminAccount, Model model)
    {
        try
        {
            Account account = accountService.generateNewAccount();
            adminAccountService.addAdminAccount(adminAccount, account);

            return "redirect:/login";
        }
        catch (Exception ex)
        {
            model.addAttribute("message", "admin account exists");
            return "registration";
        }
    }
}
