package za.co.multishare.repository.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.multishare.domain.entity.UserInfoDetail;

import java.util.List;

@Repository
public interface UserInfoDetailRepository extends JpaRepository<UserInfoDetail, Long> {

    Page<UserInfoDetail> findByUserInfoUserInfoId(@Param("userInfoId") Long userInfoId, Pageable pageable);

    UserInfoDetail findByUserInfoUserInfoIdAndRecordValidToRecordIsNull(@Param("userInfoId") Long userInfoId);

    List<UserInfoDetail> findByNameContainingOrSurnameContainingAndRecordValidToRecordIsNull(@Param("searchQuerySurname") String surnameQuery,
                                                                                             @Param("searchQueryName") String nameQuery);

}
