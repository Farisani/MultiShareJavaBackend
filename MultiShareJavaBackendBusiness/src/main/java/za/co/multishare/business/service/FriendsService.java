package za.co.multishare.business.service;

import za.co.multishare.domain.entity.FriendshipInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface FriendsService {

    Long sendFriendRequest(Long friendshipSourceId,
                           Long friendshipDestinationId,
                           LocalDateTime recordValidFromDate,
                           LocalDateTime recordValidToDate);

    void acceptFriendRequest(Long friendShipDestinationUserInfoId,
                             Long friendShipInfoId,
                             LocalDateTime recordValidFromDate,
                             LocalDateTime recordValidToDate);

    List<FriendshipInfo> findFriendships(Long userInfoId);
}
