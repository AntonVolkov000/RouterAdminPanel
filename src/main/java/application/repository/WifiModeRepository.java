package application.repository;

import application.model.WifiMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WifiModeRepository extends JpaRepository<WifiMode, Long> {
    WifiMode findByModeName(String modeName);
    WifiMode getWifiModeByModeName(String modeName);
}
