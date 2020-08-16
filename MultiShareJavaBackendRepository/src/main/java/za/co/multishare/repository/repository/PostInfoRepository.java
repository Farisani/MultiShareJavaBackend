package za.co.multishare.repository.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.multishare.domain.entity.PostInfo;

import java.util.List;

@Repository
public interface PostInfoRepository extends JpaRepository<PostInfo, Long> {

    Page<PostInfo> findByUserInfoUserInfoIdAndRecordValidToDateIsNull(@Param("userInfoId") Long userInfoId,
                                                            Pageable pageable);

    List<PostInfo> findByUserInfoUserInfoIdAndRecordValidToDateIsNull(@Param("userInfoId") Long userInfoId);
}
