package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.repository.repository.UserInfoRepository;
import za.co.multishare.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(final UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }
}
