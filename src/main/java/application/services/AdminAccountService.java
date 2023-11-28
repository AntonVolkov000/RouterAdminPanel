package application.services;

import application.model.Account;
import application.model.AdminAccount;
import application.model.Recipient;
import application.model.Role;
import application.repository.AdminAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class AdminAccountService implements UserDetailsService {
    private final AdminAccountRepository adminAccountRepository;
    @Autowired
    public AdminAccountService( AdminAccountRepository adminAccountRepository)
    { this.adminAccountRepository = adminAccountRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {
        AdminAccount admin = adminAccountRepository.findByLogin(login);
        return new org.springframework.security.core.userdetails.User(
                admin.getLogin(),
                admin.getPassword(), mapRolesToAuthorities(admin.getRoles()));
    }

    private List<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles)
    {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect(Collectors.toList());
    }

    public AdminAccount editAdminAccount(Long id) {
        return adminAccountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin_account Id:" + id));
    }

    public List<AdminAccount> getAllAdminAccounts() {
        return adminAccountRepository.findAll();
    }

    public void updateAdminAccount(AdminAccount adminAccount, Account account) {
        if (isNull(adminAccount.getAccount()))
        {
            adminAccount.setAccount(account);
        }
        adminAccountRepository.save(adminAccount);
    }

    public AdminAccount getAdminAccountById(Long id) {
        return adminAccountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
    }

    public void appendRecipient(Long id, Recipient recipient) {
        AdminAccount adminAccount = getAdminAccountById(id);
        adminAccount.getRecipients().add(recipient);
        adminAccountRepository.save(adminAccount);
    }

    public void deleteRecipient(Long id, Long recipientId) {
        AdminAccount adminAccount = getAdminAccountById(id);
        Set<Recipient> recipients = adminAccount.getRecipients();
        recipients.removeIf(recipient -> recipient.getRecipientId() == recipientId);
        adminAccountRepository.save(adminAccount);
    }

    public List<Recipient> getAllRecipients(Long id) {
        AdminAccount adminAccount = getAdminAccountById(id);
        Set<Recipient> recipients = adminAccount.getRecipients();
        return recipients.stream().toList();
    }
}
