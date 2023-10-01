package application.controller;

import application.model.AdminAccount;
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

@Controller
public class AdminAccountController {
    private final AdminAccountRepository adminAccountRepository;
    @Autowired
    public AdminAccountController(AdminAccountRepository adminAccountRepository) {
        this.adminAccountRepository = adminAccountRepository;
    }

    @GetMapping("/admins")
    public ResponseEntity<List<AdminAccount>> getAllAdminsAccount(@RequestParam(required = false) String login)
    {
        try{
            List<AdminAccount> accounts = new ArrayList<>();
            if (login == null)
                accounts.addAll(adminAccountRepository.findAll());
            else
                accounts.addAll(adminAccountRepository.findByLogin(login));

            if (accounts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(accounts, HttpStatus.OK);

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
        return "index";
    }
    @PostMapping("/addAdminAccount")
    public String addUser(@Valid AdminAccount adminAccount, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-adminAccount";
        }
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
        adminAccountRepository.save(adminAccount);
        model.addAttribute("adminAccounts", adminAccountRepository.findAll());
        return "index";
    }
}
