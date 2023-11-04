package application.services;

import application.model.Account;
import application.model.AdminAccount;
import application.model.Role;
import application.model.TelegramUser;
import application.repository.AdminAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
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
                admin.getPassword(), mapRolesToAthorities(admin.getRoles()));
    }

    private List<? extends GrantedAuthority> mapRolesToAthorities(Set<Role> roles)
    {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect(Collectors.toList());
    }

    public void addAdminAccount(AdminAccount adminAccount, Account account) throws Exception {
        AdminAccount userFromDb = adminAccountRepository.findByLogin(adminAccount.getLogin());
        if (userFromDb != null)
        {
            throw new Exception("admin account exist");
        }
        adminAccount.setAccount(account);
        adminAccount.setRoles(Collections.singleton(Role.USER));
        adminAccount.setActive(true);
        adminAccountRepository.save(adminAccount);
    }

    public AdminAccount editAdminAccount(Long id) {
        return adminAccountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin_account Id:" + id));
    }

    public void deleteAdminAccount(Long id) {
        AdminAccount adminAccount = adminAccountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        adminAccountRepository.delete(adminAccount);
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

    public AdminAccount getAdminAccountByLogin(String login) {
        return adminAccountRepository.findByLogin(login);
    }

    public AdminAccount getAdminAccountById(Long id) {
        return adminAccountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
    }

    public void loadData(Account account, List<TelegramUser> telegramUsers) {
        AdminAccount admin1 = new AdminAccount();
        admin1.setLogin("admin1");
        admin1.setPassword("1");
        admin1.setActive(false);
        adminAccountRepository.save(admin1);
        admin1.setAccount(account);
        admin1.setTelegramUsers(new HashSet<>(telegramUsers));
        adminAccountRepository.save(admin1);
    }
}
