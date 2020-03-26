package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.repository.repository.UserInfoDetailRepository;
import za.co.multishare.service.UserDetailService;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    private final UserInfoDetailRepository userInfoDetailRepository;

    @Autowired
    public UserDetailServiceImpl(final UserInfoDetailRepository userInfoDetailRepository) {
        this.userInfoDetailRepository = userInfoDetailRepository;
    }
}
