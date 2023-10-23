package application.repository;

import application.model.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long>{
    AdminAccount findByLogin(String login);
}
