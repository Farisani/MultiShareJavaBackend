package za.co.multishare.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.multishare.domain.entity.LoginInfo;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, Long> {
}
