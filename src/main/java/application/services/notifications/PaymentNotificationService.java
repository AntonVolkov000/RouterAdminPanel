package application.services.notifications;

import application.model.AdminAccount;
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
             return MessageFormat.format(
                     "Здравствуйте, {0}!" +
                     "У вас недостаточно средств для оплаты услуги интернета. " +
                     "Количество средств на счёте: {1}", adminAccount.getLogin(), accountSum);
        }
       return MessageFormat.format(
               "Здравствуйте, {0}! " +
               "Завтра у вас со счёта будет списано {1} рублей" +
               "для оплаты услуги интернета.", adminAccount.getLogin(), routerServicesPayment);
    }

    @Scheduled(cron = "${PAYMENT_DATE_TIME}") // формат см. в application.properties
    public void getRouterServicesPayment() throws InterruptedException {
        List<AdminAccount> adminAccounts = adminAccountService.getAllAdminAccounts();
        String subject = "Оплата услуг интернета";
        String message = "";
        for (AdminAccount adminAccount: adminAccounts) {
            message = getRouterServicesPaymentMessage(adminAccount);
            sendEmailService.notifyAdminAccountRecipients(adminAccount, subject, message);
        }
    }
}
