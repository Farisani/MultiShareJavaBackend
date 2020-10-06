package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.entity.FriendshipInfo;
import za.co.multishare.domain.entity.PostInfoDetail;
import za.co.multishare.repository.ContactInfoRepository;
import za.co.multishare.repository.FriendshipInfoRepository;
import za.co.multishare.repository.LoginInfoRepository;
import za.co.multishare.repository.PostInfoDetailResourceRepository;
import za.co.multishare.repository.PostInfoDetailsRepository;
import za.co.multishare.repository.PostInfoRepository;
import za.co.multishare.repository.UserInfoDetailRepository;
import za.co.multishare.repository.UserInfoRepository;
import za.co.multishare.repository.UserRoleInfoRepository;
import za.co.multishare.service.UserDeleteService;

import javax.transaction.Transactional;

@Service
public class UserDeleteServiceImpl implements UserDeleteService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoDetailRepository userInfoDetailRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final FriendshipInfoRepository friendshipInfoRepository;
    private final LoginInfoRepository loginInfoRepository;
    private final PostInfoRepository postInfoRepository;
    private final PostInfoDetailsRepository postInfoDetailsRepository;
    private final UserRoleInfoRepository userRoleInfoRepository;

    @Autowired
    public UserDeleteServiceImpl(final UserInfoRepository userInfoRepository,
                                 final UserInfoDetailRepository userInfoDetailRepository,
                                 final ContactInfoRepository contactInfoRepository,
                                 final FriendshipInfoRepository friendshipInfoRepository,
                                 final LoginInfoRepository loginInfoRepository,
                                 final PostInfoRepository postInfoRepository,
                                 final PostInfoDetailsRepository postInfoDetailsRepository,
                                 final UserRoleInfoRepository userRoleInfoRepository) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoDetailRepository = userInfoDetailRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.friendshipInfoRepository = friendshipInfoRepository;
        this.loginInfoRepository = loginInfoRepository;
        this.postInfoRepository = postInfoRepository;
        this.postInfoDetailsRepository = postInfoDetailsRepository;
        this.userRoleInfoRepository = userRoleInfoRepository;
    }

    @Override
    @Transactional
    public Boolean deleteUser(Long userInfoId) {

        try {
            postInfoDetailsRepository.deleteByPostInfoUserInfoUserInfoId(userInfoId);
            postInfoRepository.deleteByUserInfoUserInfoId(userInfoId);
            loginInfoRepository.deleteByUserInfoUserInfoId(userInfoId);
            contactInfoRepository.deleteByUserInfoUserInfoId(userInfoId);
            userRoleInfoRepository.deleteByUserInfoUserInfoId(userInfoId);
            userInfoDetailRepository.deleteByUserInfoUserInfoId(userInfoId);
            friendshipInfoRepository.deleteByDestFriendshipUserInfoUserInfoIdOrSrcFriendshipUserInfoUserInfoId(userInfoId, userInfoId);
            userInfoRepository.deleteByUserInfoId(userInfoId);
            return Boolean.TRUE;
        } catch (Exception exception) {
            return Boolean.FALSE;
        }
    }
}
