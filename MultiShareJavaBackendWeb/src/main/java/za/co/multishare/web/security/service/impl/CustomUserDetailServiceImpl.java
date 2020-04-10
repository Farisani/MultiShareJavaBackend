package za.co.multishare.web.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.co.multishare.business.service.ContactInfoService;
import za.co.multishare.business.service.LoginInfoService;
import za.co.multishare.business.service.RoleManagerService;
import za.co.multishare.domain.entity.ContactInfo;
import za.co.multishare.domain.entity.LoginInfo;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.domain.entity.UserRoleInfo;
import za.co.multishare.web.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service("customUserDetailService")
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private final ContactInfoService contactInfoService;
    private final RoleManagerService roleManagerService;
    private final LoginInfoService loginInfoService;

    @Autowired
    public CustomUserDetailServiceImpl(final ContactInfoService contactInfoService,
                                       final RoleManagerService roleManagerService,
                                       final LoginInfoService loginInfoService) {
        this.contactInfoService = contactInfoService;
        this.roleManagerService = roleManagerService;
        this.loginInfoService = loginInfoService;
    }

    @Override
    public UserDetails loadUserByUsername(final String emailAddress) throws UsernameNotFoundException {
        final ContactInfo contactInfo = contactInfoService.findActive(emailAddress);

        if (contactInfo == null) {
            throw new UsernameNotFoundException("User not found");
        }

        final UserInfo userInfo = contactInfo.getUserInfo();

        final LoginInfo loginInfo = loginInfoService.getLoginInfo(userInfo.getUserInfoId());

        final List<UserRoleInfo> userRoleInfoList = roleManagerService
                .getActiveUserRoles(userInfo.getUserInfoId());

        final List<String> roleList = userRoleInfoList
                .stream()
                .map(UserRoleInfo::getUserRole)
                .collect(Collectors.toList());

        return new User(contactInfo.getContact(), loginInfo.getPassword(), roleList);
    }
}
