package za.co.multishare.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.multishare.web.domain.entity.ContactInfo;

import java.util.List;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {

    Page<ContactInfo> findAllByUserInfoUserInfoId(@Param("userInfoId") Long userInfoId, Pageable page);

    List<ContactInfo> findAllByUserInfoUserInfoIdAndRecordValidToDateIsNull(@Param("userInfoId") Long userInfoId);

    List<ContactInfo> findByContactContainingAndRecordValidToDateIsNull(@Param("contact") String contact);

    ContactInfo findByContactAndRecordValidToDateIsNull(@Param("contact")String contact);
}
