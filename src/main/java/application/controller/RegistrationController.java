package application.controller;

import application.model.AdminAccount;
import application.services.AccountService;
import application.services.AdminAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AdminAccount adminAccount = adminAccountService.getAdminAccountByLogin(auth.getName());
        model.addAttribute("adminAccount", adminAccount);
        return "index";
    }
}
