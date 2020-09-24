package za.co.multishare.web.service;

import za.co.multishare.web.domain.constant.FriendshipInfoStateEnum;
import za.co.multishare.web.domain.entity.FriendshipInfo;
import za.co.multishare.web.domain.entity.UserInfo;

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

    List<FriendshipInfo> findFriendshipByUserInfoId(Long userInfoId);
}
