package za.co.multishare.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.multishare.domain.entity.UserInfoDetail;

@Repository
public interface UserInfoDetailRepository extends JpaRepository<UserInfoDetail, Long> {
}
