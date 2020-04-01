package za.co.multishare.business.service;

import java.time.LocalDateTime;

public interface FriendsService {

    Long sendFriendRequest(Long friendshipSourceId,
                           Long friendshipDestinationId,
                           LocalDateTime recordValidFromDate,
                           LocalDateTime recordValidToDate);

    void acceptFriendRequest(Long friendShipDestinationUserInfoId,
                             Long friendShipInfoId,
                             LocalDateTime recordValidFromDate,
                             LocalDateTime recordValidToDate);
}
