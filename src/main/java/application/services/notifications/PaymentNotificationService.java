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
    private final static double routerServicesPayment = 500.00;
    private final static String messageForPaymentCritical = "Здравствуйте, {0}!\n" +
            "У вас недостаточно средств для оплаты услуги интернета. " +
            "Количество средств на счёте: {1}";
    private final static String messageForPaymentSuccess = "Здравствуйте, {0}!\n" +
            "Завтра у вас со счёта будет списано {1} рублей " +
            "для оплаты услуги интернета.";

    public PaymentNotificationService(AdminAccountService adminAccountService, SendEmailService sendEmailService) {
        this.adminAccountService = adminAccountService;
        this.sendEmailService = sendEmailService;
    }

    private String getRouterServicesPaymentMessage(AdminAccount adminAccount) {
        double accountSum = adminAccount.getAccount().getSum();
        if (accountSum < routerServicesPayment) {
             return MessageFormat.format(messageForPaymentCritical, adminAccount.getLogin(), accountSum);
        }
       return MessageFormat.format(messageForPaymentSuccess, adminAccount.getLogin(), routerServicesPayment);
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
