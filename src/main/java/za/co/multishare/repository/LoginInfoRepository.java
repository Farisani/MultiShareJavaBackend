package za.co.multishare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.multishare.domain.entity.LoginInfo;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, Long> {

    LoginInfo findByUserInfoUserInfoIdAndRecordValidToDateIsNull(@Param("userInfoId") Long userInfoId);

    void deleteByUserInfoUserInfoId(@Param("userInfoId") Long userInfoId);

}
