package za.co.multishare.business.service;

import za.co.multishare.domain.constant.FriendshipInfoStateEnum;
import za.co.multishare.domain.entity.FriendshipInfo;
import za.co.multishare.domain.entity.UserInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface FriendshipInfoService {


    FriendshipInfo createFriendshipInfo(UserInfo srcFriendshipUserInfo,
                                        UserInfo destFriendshipUserInfo,
                                        FriendshipInfoStateEnum friendshipInfoState,
                                        LocalDateTime recordValidFromDate,
                                        LocalDateTime recordValidToDate);

    List<FriendshipInfo> findAllFriendships(UserInfo userInfoOne,
                                            UserInfo userInfoTwo);

    FriendshipInfo findFriendShipInfoId(Long friendshipInfoId);

    List<FriendshipInfo> updateFriendshipInfo(List<FriendshipInfo> friendshipInfoList);

    List<FriendshipInfo> findFriendshipByUserInfoId(Long userInfoId, Integer pageNumber, Integer pageSize);

    List<FriendshipInfo> findFriendshipByUserInfoId(Long userInfoId);
}
