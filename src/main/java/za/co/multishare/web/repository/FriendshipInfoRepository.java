package za.co.multishare.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.multishare.web.domain.entity.FriendshipInfo;

import java.util.List;

@Repository
public interface FriendshipInfoRepository extends JpaRepository<FriendshipInfo, Long> {

    List<FriendshipInfo> findAllBySrcFriendshipUserInfoUserInfoIdAndRecordValidToDateIsNull(@Param("userInfoId") Long userInfoId);
}
