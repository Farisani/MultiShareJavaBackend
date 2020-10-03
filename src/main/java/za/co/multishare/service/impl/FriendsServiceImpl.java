package za.co.multishare.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.dto.FriendDto;
import za.co.multishare.domain.dto.UserDetailsDto;
import za.co.multishare.domain.entity.ContactInfo;
import za.co.multishare.domain.entity.UserInfoDetail;
import za.co.multishare.repository.UserInfoDetailRepository;
import za.co.multishare.service.ContactInfoService;
import za.co.multishare.service.FriendsService;
import za.co.multishare.service.FriendshipInfoService;
import za.co.multishare.service.UserDetailService;
import za.co.multishare.service.UserInfoDetailsRetrievalService;
import za.co.multishare.service.UserInfoService;
import za.co.multishare.domain.constant.FriendshipInfoStateEnum;
import za.co.multishare.domain.entity.FriendshipInfo;
import za.co.multishare.domain.entity.UserInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendsServiceImpl implements FriendsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendsServiceImpl.class);

    private final FriendshipInfoService friendshipInfoService;
    private final UserInfoService userInfoService;
    private final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService;
    private final ContactInfoService contactInfoService;
    final UserInfoDetailRepository userInfoDetailRepository;

    @Autowired
    public FriendsServiceImpl(final FriendshipInfoService friendshipInfoService,
                              final UserInfoService userInfoService,
                              final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService,
                              final ContactInfoService contactInfoService,
                              final UserInfoDetailRepository userInfoDetailRepository) {
        this.friendshipInfoService = friendshipInfoService;
        this.userInfoService = userInfoService;
        this.userInfoDetailsRetrievalService = userInfoDetailsRetrievalService;
        this.contactInfoService = contactInfoService;
        this.userInfoDetailRepository = userInfoDetailRepository;
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

    @Override
    public List<FriendDto> getFriendSuggestions(final Long userInfoId) {
        final List<FriendDto> friendDtoList = new ArrayList<>();
        final List<FriendshipInfo> friendshipInfoList = findFriendships(userInfoId);

        final List<UserInfo> userInfoList = userInfoService.findAll(0, 100);

        final List<UserInfo> userInfosResults = userInfoList.stream().filter(userInfo ->
                !friendshipInfoList.stream()
                        .map(friendshipInfo ->
                                friendshipInfo.getSrcFriendshipUserInfo()
                                        .getUserInfoId())
                        .collect(Collectors.toList())
                        .contains(userInfo.getUserInfoId()) &&
                !friendshipInfoList.stream()
                        .map(friendshipInfo ->
                                friendshipInfo.getDestFriendshipUserInfo()
                                        .getUserInfoId())
                        .collect(Collectors.toList())
                        .contains(userInfo.getUserInfoId()))
                .collect(Collectors.toList());

        userInfosResults.forEach(userInfo -> {
            final UserDetailsDto userDetailsDto = userInfoDetailsRetrievalService.getUserDetails(userInfo.getUserInfoId());
            friendDtoList.add(new FriendDto(userInfo.getUserInfoId(), userDetailsDto.getSurname(),
                    userDetailsDto.getName(), "NOT_FRIENDS", false));
        });

        return friendDtoList;
    }

    @Override
    public List<FriendDto> search(final Long userInfoId, final String searchCriteria, final String searchQuery) {
        final List<UserInfo> userInfoList = new ArrayList<>();

        if ("contact".equalsIgnoreCase(searchCriteria)) {
            List<ContactInfo> contactInfoList = contactInfoService.search(searchQuery);
            userInfoList.addAll(contactInfoList.stream().map(ContactInfo::getUserInfo).collect(Collectors.toList()));
        } else {
            userInfoList.addAll(userInfoDetailRepository.findByNameContainingOrSurnameContainingAndRecordValidToRecordIsNull(searchQuery,
                    searchQuery).stream().map(UserInfoDetail::getUserInfo).collect(Collectors.toList()));
        }

        return userInfoList.stream().map(
                userInfo -> buildFriendDto(userInfoId,
                        userInfoDetailsRetrievalService.getUserDetails(userInfo.getUserInfoId()))).collect(Collectors.toList());
    }

    private FriendDto buildFriendDto(final Long userInfoId, final UserDetailsDto userDetailsDto) {
        final List<FriendshipInfo> friendshipInfoList = findFriendships(userDetailsDto.getId());
        boolean canAccept = false;
        String friendshipStatus = "NOT_FRIENDS";

        if(friendshipInfoList.stream().map(friendshipInfo ->
                friendshipInfo.getDestFriendshipUserInfo().getUserInfoId())
                .collect(Collectors.toList())
                .contains(userInfoId) ||
                friendshipInfoList.stream().map(friendshipInfo ->
                        friendshipInfo.getSrcFriendshipUserInfo().getUserInfoId())
                        .collect(Collectors.toList())
                        .contains(userInfoId)) {

            final FriendshipInfo friendshipInfoResult = friendshipInfoList.stream().filter(friendshipInfo ->
                    friendshipInfo.getSrcFriendshipUserInfo().getUserInfoId().equals(userInfoId)
                    || friendshipInfo.getDestFriendshipUserInfo().getUserInfoId().equals(userInfoId))
                    .findAny()
                    .orElse(null);

            if (friendshipInfoResult != null) {
                friendshipStatus = friendshipInfoResult.getFriendshipInfoStatus();
            }

            if (friendshipInfoList.stream().map(friendshipInfo ->
                    friendshipInfo.getDestFriendshipUserInfo().getUserInfoId())
                    .collect(Collectors.toList())
                    .contains(userInfoId)) {
                canAccept = true;
            }
        }
        return new FriendDto(userDetailsDto.getId(), userDetailsDto.getSurname(), userDetailsDto.getName(), friendshipStatus, canAccept);
    }
}
