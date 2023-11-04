package application.repository;

import application.model.GeneralConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralConfigRepository extends JpaRepository<GeneralConfig, Long> {
}
