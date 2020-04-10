package za.co.multishare.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.multishare.domain.entity.UserRoleInfo;

import java.util.List;

@Repository
public interface UserRoleInfoRepository extends JpaRepository<UserRoleInfo, Long> {

    List<UserRoleInfo> findByUserInfoUserInfoIdAndRecordValidToDateIsNull(@Param("userInfoId")Long userInfoId);
}
