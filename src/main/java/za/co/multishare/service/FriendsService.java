package za.co.multishare.service;

import za.co.multishare.domain.dto.FriendDto;
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

    List<FriendshipInfo> findFriendships(Long userInfoId,
                                         Integer pageNumber,
                                         Integer pageSize);

    List<FriendshipInfo> findFriendships(Long userInfoId);

    List<FriendDto> getFriendSuggestions(Long userId);

    List<FriendDto> search(Long userInfoId, String searchCriteria, String searchQuery);
}
