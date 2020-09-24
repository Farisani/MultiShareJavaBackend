package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.domain.entity.LoginInfo;
import za.co.multishare.repository.LoginInfoRepository;
import za.co.multishare.service.LoginInfoService;

import java.time.LocalDateTime;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {

    private final LoginInfoRepository loginInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginInfoServiceImpl(final LoginInfoRepository loginInfoRepository,
                                final PasswordEncoder passwordEncoder) {
        this.loginInfoRepository = loginInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginInfo createLoginInfo(final String password,
                                     final LocalDateTime recordValidFromDate,
                                     final LocalDateTime recordValidToDate,
                                     final UserInfo userInfo) {
         final LoginInfo loginInfo = new LoginInfo(null, passwordEncoder.encode(password),
                 recordValidFromDate, recordValidToDate, userInfo);
          return loginInfoRepository.save(loginInfo);
    }

    @Override
    public void deleteLoginInfo(Long loginInfoId, LocalDateTime recordValidToDate) {
        final LoginInfo loginInfo = loginInfoRepository.getOne(loginInfoId);
        loginInfo.setRecordValidToDate(recordValidToDate);
        loginInfoRepository.save(loginInfo);
    }

    @Override
    public LoginInfo getLoginInfo(Long userInfoId) {
        return loginInfoRepository.findByUserInfoUserInfoIdAndRecordValidToDateIsNull(userInfoId);
    }
}
