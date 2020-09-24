package za.co.multishare.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.multishare.web.domain.entity.PostInfoDetailResource;

import java.util.List;

@Repository
public interface PostInfoDetailResourceRepository extends JpaRepository<PostInfoDetailResource, Long> {

    List<PostInfoDetailResource> findByPostInfoDetailPostInfoDetailIdAndRecordValidToDateIsNull(@Param("postInfoDetailId") Long postInfoDetailId);

    void deleteByPostInfoDetailPostInfoPostInfoId(@Param("postInfoId") Long postInfoId);
}
