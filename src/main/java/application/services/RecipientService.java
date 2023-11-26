package application.services;

import application.model.Recipient;
import application.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;

@Component
public class RecipientService {
    private final RecipientRepository recipientRepository;
    @Autowired
    public RecipientService(RecipientRepository recipientRepository)
    {
        this.recipientRepository = recipientRepository;
    }

    public Recipient getRecipientById(Long id) {
        return recipientRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Recipient Id: " + id));
    }
    public List<Recipient> getAllRecipients() {
        return recipientRepository.findAll();
    }

    public Recipient createRecipient(String email) {
        if (!isValidEmailAddress(email))
            return null;
        long maxId = 0;
        List<Recipient> recipients = getAllRecipients();
        for (Recipient r: recipients) {
            if (r.getEmail().equals(email))
                return null;
            if (r.getRecipientId() > maxId)
                maxId = r.getRecipientId();
        }
        long newId = maxId + 1;
        Recipient recipient = new Recipient();
        recipient.setRecipientId(newId);
        recipient.setEmail(email);
        recipientRepository.save(recipient);
        return recipient;
    }

    public void deleteRecipientById(Long id) {
        Recipient recipient = getRecipientById(id);
        recipientRepository.delete(recipient);
    }

    public void updateRecipient(Long id, String newEmail) {
        Recipient recipient = getRecipientById(id);
        String oldEmail = recipient.getEmail();
        if (oldEmail.equals(newEmail))
            return;
        if (!isValidEmailAddress(newEmail))
            return;
        List<Recipient> recipients = getAllRecipients();
        for (Recipient dbRecipients: recipients) {
            if (dbRecipients.getEmail().equals(newEmail))
                return;
        }
        recipient.setEmail(newEmail);
        recipientRepository.save(recipient);
    }

    public static boolean isValidEmailAddress(String email) {
        boolean success = true;
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return success;
        } catch (AddressException ex) {
            return !success;
        }
    }
}
