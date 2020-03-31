package za.co.multishare.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.business.service.ContactInfoService;
import za.co.multishare.business.service.UserDetailService;
import za.co.multishare.business.service.UserInfoDetailsRetrievalService;
import za.co.multishare.domain.dto.ContactDetailsInfoDto;
import za.co.multishare.domain.dto.UserDetailsDto;
import za.co.multishare.domain.entity.ContactInfo;
import za.co.multishare.domain.entity.UserInfoDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserInfoDetailsRetrievalServiceImpl implements UserInfoDetailsRetrievalService {

    private static Logger LOGGER = Logger.getLogger("UserInfoDetailsRetrievalServiceImpl");

    private final UserDetailService userDetailService;
    private final ContactInfoService contactInfoService;

    @Autowired
    public UserInfoDetailsRetrievalServiceImpl(final UserDetailService userDetailService,
                                               final ContactInfoService contactInfoService) {
        this.userDetailService = userDetailService;
        this.contactInfoService = contactInfoService;
    }

    @Override
    public UserDetailsDto getUserDetails(Long userId) {
        final List<ContactInfo> contactInfoList = contactInfoService.findActive(userId);

        final UserInfoDetail userInfoDetail = userDetailService.findActive(userId);

        final List<ContactDetailsInfoDto> contactDetailsInfoDtoList = new ArrayList<>();

        contactInfoList.forEach(contactInfo -> {
            contactDetailsInfoDtoList.add(new ContactDetailsInfoDto(contactInfo.getContact(),
                    contactInfo.getContactType()));
        });

        return new UserDetailsDto(userId, userInfoDetail.getTitle(), userInfoDetail.getName(),
                userInfoDetail.getSurname(), userInfoDetail.getGender(), contactDetailsInfoDtoList,
                userInfoDetail.getLegalIdentityNumber());
    }
}
