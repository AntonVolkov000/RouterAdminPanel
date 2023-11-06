package application.repository;

import application.model.Wifi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WifiRepository extends JpaRepository<Wifi, Long> {
}
