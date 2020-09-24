package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.domain.entity.UserRoleInfo;
import za.co.multishare.service.AuthenticationService;
import za.co.multishare.service.JwtTokenProvider;
import za.co.multishare.service.RoleManagerService;
import za.co.multishare.service.UserInfoDetailsRetrievalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService;
    private final RoleManagerService roleManagerService;

    @Autowired
    public AuthenticationServiceImpl(final AuthenticationManager authenticationManager,
                                     final JwtTokenProvider jwtTokenProvider,
                                     final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService,
                                     final RoleManagerService roleManagerService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userInfoDetailsRetrievalService = userInfoDetailsRetrievalService;
        this.roleManagerService = roleManagerService;
    }

    @Override
    public Map<String, String> authenticate(final String username,
                                            final String password) {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            final UserInfo userInfo = userInfoDetailsRetrievalService.searchForUserByContact(username);
            final List<String> roles = roleManagerService.getActiveUserRoles(userInfo.getUserInfoId())
                    .stream()
                    .map(UserRoleInfo::getUserRole)
                    .collect(Collectors.toList());

            final String token = jwtTokenProvider.createToken(username, roles);
            final Map<String, String> model = new HashMap<>();

            model.put("userId", userInfo.getUserInfoId().toString());
            model.put("token", token);
            return model;
        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("Invalid username/password");
        }
    }
}
