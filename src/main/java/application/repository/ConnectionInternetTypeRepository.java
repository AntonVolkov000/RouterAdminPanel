package application.repository;

import application.model.ConnectionInternetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionInternetTypeRepository extends JpaRepository<ConnectionInternetType, Long> {
}
