package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.repository.repository.LoginInfoRepository;
import za.co.multishare.service.LoginInfoService;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {

    private final LoginInfoRepository loginInfoRepository;

    @Autowired
    public LoginInfoServiceImpl(final LoginInfoRepository loginInfoRepository) {
        this.loginInfoRepository = loginInfoRepository;
    }
}
