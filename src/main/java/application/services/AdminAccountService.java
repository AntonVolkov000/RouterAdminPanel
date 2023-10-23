package application.services;

import application.model.AdminAccount;
import application.model.Account;
import application.model.Role;
import application.repository.AdminAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        System.out.println("load by username");
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

//    public void addAdminAccount(AdminAccount adminAccount) throws Exception
//    {
//
//        System.out.println("add admin account");
//        System.out.println(adminAccount.getLogin());
//        System.out.println(adminAccount.getPassword());
//        AdminAccount userFromDb = adminAccountRepository.findByLogin(adminAccount.getLogin());
//        if (userFromDb != null)
//        {
//            throw new Exception("user exist");
//        }
//        adminAccount.setRoles(Collections.singleton(Role.USER));
//        adminAccount.setActive(true);
//        adminAccountRepository.save(adminAccount);
//        AdminAccount finded = adminAccountRepository.findByLogin(adminAccount.getLogin());
//        System.out.println(finded.getLogin());
//        System.out.println(finded.getPassword());
//        System.out.println("add finish");
//    }

    public void addAdminAccount(AdminAccount adminAccount, Account account) throws Exception {
        AdminAccount userFromDb = adminAccountRepository.findByLogin(adminAccount.getLogin());
        if (userFromDb != null)
        {
            throw new Exception("admin account exist");
        }
        System.out.println("add admin account");
        adminAccount.setAccount(account);
        adminAccount.setRoles(Collections.singleton(Role.USER));
        adminAccount.setActive(true);
        adminAccountRepository.save(adminAccount);
        AdminAccount finded = adminAccountRepository.findByLogin(adminAccount.getLogin());
        System.out.println(finded.getLogin());
    }
}
