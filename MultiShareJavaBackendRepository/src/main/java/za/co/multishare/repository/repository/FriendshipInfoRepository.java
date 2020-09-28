package za.co.multishare.repository.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.multishare.domain.entity.FriendshipInfo;

import java.util.List;

@Repository
public interface FriendshipInfoRepository extends JpaRepository<FriendshipInfo, Long> {

    List<FriendshipInfo> findAllBySrcFriendshipUserInfoUserInfoIdAndRecordValidToDateIsNull(@Param("userInfoId") Long userInfoId,
                                                                                            Pageable page);

    List<FriendshipInfo> findAllBySrcFriendshipUserInfoUserInfoIdAndRecordValidToDateIsNull(@Param("userInfoId") Long userInfoId);
}
