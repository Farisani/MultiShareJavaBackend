package za.co.multishare.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.multishare.domain.dto.AdminUserDetailsDto;
import za.co.multishare.domain.entity.UserInfo;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    @Query(value = "select * from user_info ui where ui.record_valid_from_date between :startDate and :endDate",
            nativeQuery = true)
    List<UserInfo> findUserInfoByRecordValidFromDateBetweenStartDateAndEndDate(@Param("startDate") LocalDateTime startDate,
                                                                               @Param("endDate") LocalDateTime endDate,
                                                                               Pageable pageable);

    void deleteByUserInfoId(@Param("userInfoId") Long userInfoId);
}

