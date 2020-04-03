package za.co.multishare.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.multishare.domain.entity.PostInfoDetailResource;

import java.util.List;

@Repository
public interface PostInfoDetailResourceRepository extends JpaRepository<PostInfoDetailResource, Long> {

    List<PostInfoDetailResource> findByPostInfoDetailPostInfoDetailIdAndRecordValidToDateIsNull(@Param("postInfoDetailId") Long postInfoDetailId);
}
