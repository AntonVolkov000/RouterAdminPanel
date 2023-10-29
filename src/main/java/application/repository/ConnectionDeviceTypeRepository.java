package application.repository;

import application.model.ConnectionDeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionDeviceTypeRepository extends JpaRepository<ConnectionDeviceType, Long> {
}
