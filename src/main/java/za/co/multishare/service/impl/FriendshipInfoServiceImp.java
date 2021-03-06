package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import za.co.multishare.service.FriendshipInfoService;
import za.co.multishare.domain.constant.FriendshipInfoStateEnum;
import za.co.multishare.domain.entity.FriendshipInfo;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.repository.FriendshipInfoRepository;
import za.co.multishare.service.UserInfoDetailsRetrievalService;
import za.co.multishare.service.UserInfoService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendshipInfoServiceImp implements FriendshipInfoService {

    private final FriendshipInfoRepository friendshipInfoRepository;
    private final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService;

    @Autowired
    public FriendshipInfoServiceImp(final FriendshipInfoRepository friendshipInfoRepository,
                                    final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService) {
        this.friendshipInfoRepository = friendshipInfoRepository;
        this.userInfoDetailsRetrievalService = userInfoDetailsRetrievalService;
    }

    @Override
    public FriendshipInfo createFriendshipInfo(final UserInfo srcFriendshipUserInfo,
                                               final UserInfo destFriendshipUserInfo,
                                               final FriendshipInfoStateEnum friendshipInfoState,
                                               final LocalDateTime recordValidFromDate,
                                               final LocalDateTime recordValidToDate) {

        final FriendshipInfo friendshipInfo = new FriendshipInfo(null, recordValidFromDate,
                recordValidToDate, friendshipInfoState.name(), srcFriendshipUserInfo,
                destFriendshipUserInfo);
        return friendshipInfoRepository.save(friendshipInfo);
    }

    @Override
    public List<FriendshipInfo> findAllFriendships(final UserInfo userInfoOne,
                                                   final UserInfo userInfoTwo) {

        return friendshipInfoRepository
                .findAllBySrcFriendshipUserInfoUserInfoIdAndRecordValidToDateIsNull(
                        userInfoOne.getUserInfoId());

    }

    @Override
    public FriendshipInfo findFriendShipInfoId(Long friendshipInfoId) {
        return friendshipInfoRepository.getOne(friendshipInfoId);
    }

    @Override
    public List<FriendshipInfo> updateFriendshipInfo(List<FriendshipInfo> friendshipInfoList) {
        return friendshipInfoRepository.saveAll(friendshipInfoList);
    }

    @Override
    public List<FriendshipInfo> findFriendshipByUserInfoId(Long userInfoId, Integer pageNumber, Integer pageSize) {
        final Pageable page = PageRequest.of(pageNumber, pageSize);
        return friendshipInfoRepository.findAllBySrcFriendshipUserInfoUserInfoIdAndRecordValidToDateIsNull(userInfoId, page);
    }

    @Override
    public List<FriendshipInfo> findFriendshipByUserInfoId(Long userInfoId) {
        return friendshipInfoRepository.findAllBySrcFriendshipUserInfoUserInfoIdAndRecordValidToDateIsNull(userInfoId);
    }
}
