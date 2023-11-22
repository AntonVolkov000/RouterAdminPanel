package application.controller;

import application.model.Recipient;
import application.services.AdminAccountService;
import application.services.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recipient")
public class RecipientController {
    private final RecipientService recipientService;
    private final AdminAccountService adminAccountService;
    @Autowired
    public RecipientController(RecipientService recipientService,
                               AdminAccountService adminAccountService) {
        this.recipientService = recipientService;
        this.adminAccountService = adminAccountService;
    }

    @GetMapping("/get_by/{a_id}")
    public String getRecipients(@PathVariable("a_id") long adminAccountId, Model model){
        List<Recipient> recipients = adminAccountService.getAllRecipients(adminAccountId);
        model.addAttribute("recipients", recipients);
        model.addAttribute("adminAccountId", adminAccountId);
        return "recipient";
    }

    @GetMapping("/add_by/{a_id}")
    public String addRecipient(@PathVariable("a_id") long adminAccountId, Model model){
        model.addAttribute("adminAccountId", adminAccountId);
        return "recipient-add";
    }

    @PostMapping("/create_by/{a_id}")
    public String createRecipient(@PathVariable("a_id") long adminAccountId,
                                  String email, Model model) {
        Recipient recipient = recipientService.createRecipient(email);
        if (recipient != null)
            adminAccountService.appendRecipient(adminAccountId, recipient);
        List<Recipient> recipients = adminAccountService.getAllRecipients(adminAccountId);
        model.addAttribute("recipients", recipients);
        model.addAttribute("adminAccountId", adminAccountId);
        return "recipient";
    }

    @GetMapping("/delete/{a_id}/{r_id}")
    public String deleteRecipient(@PathVariable("a_id") long adminAccountId,
                                  @PathVariable("r_id") long recipientId,
                                  Model model) {
        adminAccountService.deleteRecipient(adminAccountId, recipientId);
        recipientService.deleteRecipientById(recipientId);
        List<Recipient> recipients = adminAccountService.getAllRecipients(adminAccountId);
        model.addAttribute("recipients", recipients);
        model.addAttribute("adminAccountId", adminAccountId);
        return "recipient";
    }

    @GetMapping("/edit/{a_id}/{r_id}")
    public String editRecipient(@PathVariable("a_id") long adminAccountId,
                                @PathVariable("r_id") long recipientId,
                                Model model) {
        Recipient recipient = recipientService.getRecipientById(recipientId);
        model.addAttribute("recipient", recipient);
        model.addAttribute("adminAccountId", adminAccountId);
        return "recipient-edit";
    }

    @PostMapping("/update/{a_id}/{r_id}")
    public String updateRecipient(@PathVariable("a_id") long adminAccountId,
                                  @PathVariable("r_id") long recipientId,
                                  String email, Model model) {
        recipientService.updateRecipient(recipientId, email);
        List<Recipient> recipients = adminAccountService.getAllRecipients(adminAccountId);
        model.addAttribute("recipients", recipients);
        model.addAttribute("adminAccountId", adminAccountId);
        return "recipient";
    }
}
