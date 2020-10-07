package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.dto.UserLoginInternalDto;
import za.co.multishare.domain.entity.ContactInfo;
import za.co.multishare.domain.entity.LoginInfo;
import za.co.multishare.domain.entity.UserRoleInfo;
import za.co.multishare.repository.ContactInfoRepository;
import za.co.multishare.repository.LoginInfoRepository;
import za.co.multishare.repository.UserRoleInfoRepository;
import za.co.multishare.service.UserAuthenticationService;
import za.co.multishare.util.JwtTokenUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final LoginInfoRepository loginInfoRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final UserRoleInfoRepository userRoleInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtility jwtTokenUtility;

    @Autowired
    public UserAuthenticationServiceImpl(final LoginInfoRepository loginInfoRepository,
                                         final ContactInfoRepository contactInfoRepository,
                                         final UserRoleInfoRepository userRoleInfoRepository,
                                         final PasswordEncoder passwordEncoder,
                                         final JwtTokenUtility jwtTokenUtility) {
        this.loginInfoRepository = loginInfoRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.userRoleInfoRepository = userRoleInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtility = jwtTokenUtility;
    }

    @Override
    public UserLoginInternalDto getUserLoginInformation(String username) {
        final ContactInfo contactInfo = contactInfoRepository.findByContactAndRecordValidToDateIsNull(username);

        final LoginInfo loginInfo = loginInfoRepository
                .findByUserInfoUserInfoIdAndRecordValidToDateIsNull(contactInfo.getUserInfo().getUserInfoId());

        final List<UserRoleInfo> userRoleInfoList = userRoleInfoRepository
                .findByUserInfoUserInfoIdAndRecordValidToDateIsNull(contactInfo.getUserInfo().getUserInfoId());

        return new UserLoginInternalDto(username, loginInfo.getPassword(),
                userRoleInfoList.stream()
                        .map(UserRoleInfo::getUserRole).collect(Collectors.toList()));
    }

    @Override
    public Map<String, String> authenticateUser(String username, String password) {
        try {
            final ContactInfo contactInfo = contactInfoRepository.findByContactAndRecordValidToDateIsNull(username);

            final LoginInfo loginInfo = loginInfoRepository
                    .findByUserInfoUserInfoIdAndRecordValidToDateIsNull(contactInfo.getUserInfo().getUserInfoId());

            final List<UserRoleInfo> userRoleInfoList = userRoleInfoRepository
                    .findByUserInfoUserInfoIdAndRecordValidToDateIsNull(contactInfo.getUserInfo().getUserInfoId());

            if (loginInfo != null && passwordEncoder.matches(password, loginInfo.getPassword())) {
                final UserDetails userDetails = new User(username, password,
                        userRoleInfoList.stream().map(userRoleInfo ->
                                new SimpleGrantedAuthority(userRoleInfo.getUserRole()))
                                .collect(Collectors.toList()));

                final String token = jwtTokenUtility.generateToken(userDetails);

                final Map<String, String> resultsMap = new HashMap<>();
                resultsMap.put("token", token);
                resultsMap.put("userId", contactInfo.getUserInfo().getUserInfoId().toString());

                return resultsMap;
            } else {
                throw new BadCredentialsException("Incorrect credentials provided");
            }
        } catch (BadCredentialsException exception) {
            throw exception;
        }catch (Exception exception) {
            throw new AuthenticationServiceException("Failed to authenticate. Please retry");
        }
    }
}
