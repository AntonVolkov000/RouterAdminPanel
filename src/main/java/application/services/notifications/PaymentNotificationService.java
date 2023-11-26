package application.services.notifications;

import application.model.AdminAccount;
import application.model.Recipient;
import application.services.AdminAccountService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class PaymentNotificationService {
    private final AdminAccountService adminAccountService;
    private final SendEmailService sendEmailService;

    public PaymentNotificationService(AdminAccountService adminAccountService, SendEmailService sendEmailService) {
        this.adminAccountService = adminAccountService;
        this.sendEmailService = sendEmailService;
    }

    private String getRouterServicesPaymentMessage(AdminAccount adminAccount) {
        double accountSum = -1;
        final double routerServicesPayment = 500.00;
        accountSum = adminAccount.getAccount().getSum();
        if (accountSum < routerServicesPayment) {
             return MessageFormat.format("Уважаемый {0}!Недостаточно средств для оплаты услуги интернета. " +
                            "Количество средств на счёте: {0}", accountSum);
        }
       return MessageFormat.format("Завтра у вас со счёта будет списано {0} рублей" +
               "для оплаты услуги интернета.", routerServicesPayment);
    }

    public void notifyAdminAccountRecipients(AdminAccount adminAccount, String subject, String message)
    {
        List<String> emails = adminAccount.getRecipients().stream()
                .map(Recipient::getEmail)
                .toList();
        if (emails.isEmpty())
            return;
        for (String email: emails)
        {
            sendEmailService.sendEmail(email, subject, message);
        }
    }

    @Scheduled(cron = "${PAYMENT_DATE_TIME}") // формат см. в application.properties
    public void getRouterServicesPayment() throws InterruptedException {
        List<AdminAccount> adminAccounts = adminAccountService.getAllAdminAccounts();
        String subject = "Оплата услуг интернета";
        String message = "";
        for (AdminAccount adminAccount: adminAccounts) {
            message = getRouterServicesPaymentMessage(adminAccount);
            notifyAdminAccountRecipients(adminAccount, subject, message);
        }
    }
}
