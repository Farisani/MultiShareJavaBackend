package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.co.multishare.service.LoginInfoService;
import za.co.multishare.service.RoleManagerService;
import za.co.multishare.service.UserInfoDetailsRetrievalService;
import za.co.multishare.domain.entity.LoginInfo;
import za.co.multishare.domain.dto.User;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.domain.entity.UserRoleInfo;

import java.util.List;
import java.util.stream.Collectors;

@Service("customUserDetailService")
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private final RoleManagerService roleManagerService;
    private final LoginInfoService loginInfoService;
    private final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService;

    @Autowired
    public CustomUserDetailServiceImpl(final RoleManagerService roleManagerService,
                                       final LoginInfoService loginInfoService,
                                       final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService) {
        this.roleManagerService = roleManagerService;
        this.loginInfoService = loginInfoService;
        this.userInfoDetailsRetrievalService = userInfoDetailsRetrievalService;
    }

    @Override
    public UserDetails loadUserByUsername(final String emailAddress) throws UsernameNotFoundException {
        final UserInfo userInfo  = userInfoDetailsRetrievalService.searchForUserByContact(emailAddress);

        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found");
        }

        final LoginInfo loginInfo = loginInfoService.getLoginInfo(userInfo.getUserInfoId());

        final List<UserRoleInfo> userRoleInfoList = roleManagerService
                .getActiveUserRoles(userInfo.getUserInfoId());

        final List<String> roleList = userRoleInfoList
                .stream()
                .map(UserRoleInfo::getUserRole)
                .collect(Collectors.toList());

        return new User(emailAddress, loginInfo.getPassword(), roleList);
    }
}
