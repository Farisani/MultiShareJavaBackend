package za.co.multishare.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.dto.RegistrationDto;
import za.co.multishare.business.service.UserInfoService;
import za.co.multishare.domain.dto.ContactInfoDto;
import za.co.multishare.domain.dto.UserInfoDto;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.business.service.ContactInfoService;
import za.co.multishare.business.service.LoginInfoService;
import za.co.multishare.business.service.RegistrationService;
import za.co.multishare.business.service.UserDetailService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserInfoService userInfoService;
    private final UserDetailService userDetailService;
    private final ContactInfoService contactInfoService;
    private final LoginInfoService loginInfoService;

    @Autowired
    public RegistrationServiceImpl(final UserInfoService userInfoService,
                                   final UserDetailService userDetailService,
                                   final ContactInfoService contactInfoService,
                                   final LoginInfoService loginInfoService) {
        this.userInfoService = userInfoService;
        this.userDetailService = userDetailService;
        this.contactInfoService = contactInfoService;
        this.loginInfoService = loginInfoService;
    }

    @Transactional
    @Override
    public void registerUser(final RegistrationDto registrationDto,
                             final LocalDateTime recordValidFromDate,
                             final LocalDateTime recordValidToDate) {

        final UserInfo userInfo = userInfoService
                .createUserInfo(Collections
                        .singletonList(new UserInfoDto(null,
                                recordValidFromDate, recordValidToDate))).get(0);

        final ContactInfoDto contactNumberContactInfoDto = new ContactInfoDto(userInfo.getUserInfoId(),
                registrationDto.getContactNumber(), "CONTACT_NUMBER", recordValidFromDate, recordValidToDate);

        final ContactInfoDto emailContactInfoDto = new ContactInfoDto(userInfo.getUserInfoId(),
                registrationDto.getEmailAddress(), "EMAIL", recordValidFromDate, recordValidToDate);

        contactInfoService.createContactInfo(Arrays.asList(contactNumberContactInfoDto, emailContactInfoDto));

        loginInfoService.createLoginInfo(registrationDto.getPassword(),
                recordValidFromDate, recordValidToDate, userInfo);

        userDetailService.createUserInfoDetail(registrationDto.getTitle(), registrationDto.getSurname(),
                registrationDto.getName(), registrationDto.getGender(), registrationDto.getLegalIdentityNumber(),
                recordValidFromDate, recordValidToDate, userInfo);
    }
}
