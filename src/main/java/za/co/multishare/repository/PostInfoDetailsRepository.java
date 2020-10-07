package za.co.multishare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.multishare.domain.entity.PostInfoDetail;

@Repository
public interface PostInfoDetailsRepository extends JpaRepository<PostInfoDetail, Long> {

    PostInfoDetail findByPostInfoPostInfoIdAndRecordValidToDateIsNull(@Param("postInfoId") Long postInfoIs);

    void deleteByPostInfoPostInfoId(@Param("postInfoId") Long postInfoId);

    void deleteByPostInfoUserInfoUserInfoId(@Param("userInfoId") Long userId);
}