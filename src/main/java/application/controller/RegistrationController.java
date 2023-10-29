package application.controller;

import application.model.Account;
import application.model.AdminAccount;

import application.services.AccountService;
import application.services.AdminAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            model.addAttribute("message",
                    "Аккаунт администратора с таким именем уже существует!");
            return "registration";
        }
    }

    @RequestMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AdminAccount adminAccount = adminAccountService.getAdminAccountByLogin(auth.getName());
        model.addAttribute("adminAccount", adminAccount);
        return "index";
    }
}
