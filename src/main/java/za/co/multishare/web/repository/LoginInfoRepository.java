package za.co.multishare.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.multishare.web.domain.entity.LoginInfo;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, Long> {

    LoginInfo findByUserInfoUserInfoIdAndRecordValidToDateIsNull(@Param("userInfoId") Long userInfoId);
}
