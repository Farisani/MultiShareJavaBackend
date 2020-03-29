package za.co.multishare.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.repository.repository.LoginInfoRepository;
import za.co.multishare.domain.entity.LoginInfo;
import za.co.multishare.business.service.LoginInfoService;

import java.time.LocalDateTime;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {

    private final LoginInfoRepository loginInfoRepository;

    @Autowired
    public LoginInfoServiceImpl(final LoginInfoRepository loginInfoRepository) {
        this.loginInfoRepository = loginInfoRepository;
    }

    @Override
    public LoginInfo createLoginInfo(final String password,
                                     final LocalDateTime recordValidFromDate,
                                     final LocalDateTime recordValidToDate,
                                     final UserInfo userInfo) {
         return new LoginInfo(null, password, recordValidFromDate, recordValidToDate, userInfo);
    }

    @Override
    public void deleteLoginInfo(Long loginInfoId, LocalDateTime recordValidToDate) {
        final LoginInfo loginInfo = loginInfoRepository.getOne(loginInfoId);
        loginInfo.setRecordValidToDate(recordValidToDate);
        loginInfoRepository.save(loginInfo);
    }
}