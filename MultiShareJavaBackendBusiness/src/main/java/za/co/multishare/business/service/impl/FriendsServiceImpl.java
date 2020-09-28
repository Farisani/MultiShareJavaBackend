package za.co.multishare.business.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import za.co.multishare.business.service.FriendsService;
import za.co.multishare.business.service.FriendshipInfoService;
import za.co.multishare.business.service.UserInfoService;
import za.co.multishare.domain.constant.FriendshipInfoStateEnum;
import za.co.multishare.domain.entity.FriendshipInfo;
import za.co.multishare.domain.entity.UserInfo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class FriendsServiceImpl implements FriendsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendsServiceImpl.class);

    private final FriendshipInfoService friendshipInfoService;
    private final UserInfoService userInfoService;

    @Autowired
    public FriendsServiceImpl(final FriendshipInfoService friendshipInfoService,
                              final UserInfoService userInfoService) {
        this.friendshipInfoService = friendshipInfoService;
        this.userInfoService = userInfoService;
    }

    @Override
    public Long sendFriendRequest(final Long friendshipSourceId,
                                  final Long friendshipDestinationId,
                                  final LocalDateTime recordValidFromDate,
                                  final LocalDateTime recordValidToDate) {

        //check if frienship already exist [is it pending? blocked?]
        final UserInfo srcFriendshipUserInfo = userInfoService.findById(friendshipSourceId);
        final UserInfo destFriendshipUserInfo = userInfoService.findById(friendshipDestinationId);

        if (srcFriendshipUserInfo == null || destFriendshipUserInfo == null) {
            throw new RuntimeException("Could not resolve user information");
        }

        final List<FriendshipInfo> friendshipInfoList = friendshipInfoService.findAllFriendships(srcFriendshipUserInfo,
                destFriendshipUserInfo);

        final FriendshipInfo friendshipInfo = friendshipInfoList
                .stream()
                .filter(friendshipInfoSearch ->
                        friendshipInfoSearch
                                .getSrcFriendshipUserInfo()
                                .getUserInfoId()
                                .equals(destFriendshipUserInfo.getUserInfoId()) ||
                                friendshipInfoSearch.
                                        getDestFriendshipUserInfo()
                                        .getUserInfoId()
                                        .equals(destFriendshipUserInfo.getUserInfoId()))
                .findAny()
                .orElse(null);

        if (friendshipInfo != null) {
            switch (friendshipInfo.getFriendshipInfoStatus()) {
                case "BLOCKED":
                    throw new RuntimeException("Users have blocked each other and/or blocked");
                case "FRIENDS":
                    throw new RuntimeException("Users are already friends");
                case "PENDING":
                    throw new RuntimeException("Users have a pending friend request");

            }
        }

        final FriendshipInfo newFriendshipInfo = friendshipInfoService.createFriendshipInfo(srcFriendshipUserInfo,
                destFriendshipUserInfo, FriendshipInfoStateEnum.PENDING, recordValidFromDate, recordValidToDate);

        return newFriendshipInfo.getFriendshipInfoId();
    }

    @Override
    public void acceptFriendRequest(final Long friendShipDestinationUserInfoId,
                                    final Long friendShipInfoId,
                                    final LocalDateTime recordValidFromDate,
                                    final LocalDateTime recordValidToDate) {

        final FriendshipInfo friendshipInfo = friendshipInfoService.findFriendShipInfoId(friendShipInfoId);


        if (friendshipInfo == null) {
            LOGGER.error("Could not find pending friend request");
            throw new RuntimeException("Could not find pending friend request");
        }

        if (!friendshipInfo.getFriendshipInfoStatus().equalsIgnoreCase(FriendshipInfoStateEnum.PENDING.name())) {
            LOGGER.error("Could not find active friend request: " + friendshipInfo.toString());
            throw new RuntimeException("Could not find active friend request");
        }

        if (!friendshipInfo.getDestFriendshipUserInfo().getUserInfoId().equals(friendShipDestinationUserInfoId)) {
            LOGGER.error("Not eligible to accept friend request: " + friendshipInfo.toString());
            throw new RuntimeException("Not eligible to accept friend request");
        }

        friendshipInfo.setRecordValidToDate(recordValidToDate);

        final FriendshipInfo newFriendshipInfo = friendshipInfoService
                .createFriendshipInfo(friendshipInfo.getSrcFriendshipUserInfo(),
                        friendshipInfo.getDestFriendshipUserInfo(), FriendshipInfoStateEnum.FRIENDS,
                        recordValidFromDate.plusMonths(1), recordValidToDate);

        //this will also save the new friendship info
        friendshipInfoService.updateFriendshipInfo(Arrays.asList(friendshipInfo, newFriendshipInfo));

    }

    @Override
    public List<FriendshipInfo> findFriendships(final Long userInfoId,
                                                final Integer pageNumber,
                                                final Integer pageSize) {

        return friendshipInfoService.findFriendshipByUserInfoId(userInfoId, pageNumber, pageSize);
    }

    @Override
    public List<FriendshipInfo> findFriendships(Long userInfoId) {
        return friendshipInfoService.findFriendshipByUserInfoId(userInfoId);
    }
}
