package za.co.multishare.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.business.service.FeedService;
import za.co.multishare.business.service.FriendsService;
import za.co.multishare.business.service.PostInfoService;
import za.co.multishare.business.service.UserInfoDetailsRetrievalService;
import za.co.multishare.business.service.UserInfoService;
import za.co.multishare.domain.dto.FeedDto;
import za.co.multishare.domain.dto.UserDetailsDto;
import za.co.multishare.domain.entity.FriendshipInfo;
import za.co.multishare.domain.entity.PostInfo;
import za.co.multishare.domain.entity.PostInfoDetail;
import za.co.multishare.domain.entity.PostInfoDetailResource;
import za.co.multishare.domain.entity.UserInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedServiceImpl implements FeedService {

    private final UserInfoService userInfoService;
    private final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService;
    private final PostInfoService postInfoService;
    private final FriendsService friendsService;

    @Autowired
    public FeedServiceImpl(final UserInfoService userInfoService,
                           final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService,
                           final PostInfoService postInfoService,
                           final FriendsService friendsService) {
        this.userInfoService = userInfoService;
        this.userInfoDetailsRetrievalService = userInfoDetailsRetrievalService;
        this.postInfoService = postInfoService;
        this.friendsService = friendsService;
    }

    @Override
    public List<FeedDto> getFeeds(final Long userInfoId,
                                  final Integer pageNumber,
                                  final Integer pageSize) {

        //find all friends of the current user
        //find all posts made by current user
        //find all posts made by friends of current user
        //sort all posts by date made
        //return all posts between page number and size

        final List<UserInfo> userInfoList = collectAndGetUserInfoAndFriendsUserInfoList(userInfoId);

        final List<PostInfo> postInfoList = getPostInfoList(userInfoList, pageNumber, pageSize);

        final List<FeedDto> feedDtoList = new ArrayList<>();

        postInfoList.forEach(postInfo -> {
            final UserDetailsDto userDetailsDto = userInfoDetailsRetrievalService
                    .getUserDetails(postInfo.getUserInfo().getUserInfoId());

            final PostInfoDetail postInfoDetail = postInfoService
                    .findActivePostInfoDetailByPostInfoId(postInfo.getPostInfoId());

            final List<PostInfoDetailResource> postInfoDetailResourceList = postInfoService
                    .findPostInfoDetailResourcesByPostInfoDetailId(postInfoDetail
                            .getPostInfoDetailId());

            feedDtoList.add(getFeedDto(postInfo, postInfoDetail, postInfoDetailResourceList, userDetailsDto));
        });

        return feedDtoList;
    }

    private List<PostInfo> getPostInfoList(final List<UserInfo> userInfoList,
                                           final Integer pageNumber,
                                           final Integer pageSize) {
        final List<PostInfo> postInfoList = new ArrayList<>();

        userInfoList.forEach(userInfo -> {
            postInfoList.addAll(postInfoService.findAllUserPostInfo(userInfo.getUserInfoId()));
        });

        int listStartIndex = (pageNumber - 1) * pageSize;
        int listEndIndex = (pageSize * pageNumber) - 1;

        if (!(listStartIndex < postInfoList.size())) {
            return new ArrayList<>();
        }

        while (listEndIndex > listStartIndex && listEndIndex > postInfoList.size()) {
            listEndIndex--;
        }

        return postInfoList
                .stream()
                .sorted(Comparator.comparing(PostInfo::getRecordValidFromDate))
                .collect(Collectors.toList())
                .subList(listStartIndex, listEndIndex);
    }

    private FeedDto getFeedDto(final PostInfo postInfo,
                               final PostInfoDetail postInfoDetail,
                               final List<PostInfoDetailResource> postInfoDetailResourceList,
                               final UserDetailsDto userDetailsDto) {

        return new FeedDto(postInfo.getUserInfo().getUserInfoId(),
                userDetailsDto.getName(),
                userDetailsDto.getSurname(),
                postInfoDetail.getTitle(),
                postInfoDetail.getPostBody(),
                postInfoDetailResourceList.stream()
                        .map(PostInfoDetailResource::getResource)
                        .collect(Collectors.toList()),
                postInfo.getRecordValidFromDate());
    }

    private List<UserInfo> collectAndGetUserInfoAndFriendsUserInfoList(final Long userInfoId) {
        final UserInfo sourceUserInfo = userInfoService.findById(userInfoId);

        if (sourceUserInfo == null) {
            throw new RuntimeException("Could not find user information");
        }

        final List<UserInfo> userInfoList = new ArrayList<>();

        userInfoList.add(sourceUserInfo);

        final List<FriendshipInfo> friendshipInfoList = friendsService.findFriendships(userInfoId);

        friendshipInfoList.forEach(friendshipInfo -> userInfoList.add(friendshipInfo.getDestFriendshipUserInfo()));

        return userInfoList;
    }

}
