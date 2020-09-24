package za.co.multishare.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.web.service.ContactInfoService;
import za.co.multishare.web.service.UserDetailService;
import za.co.multishare.web.service.UserInfoDetailsRetrievalService;
import za.co.multishare.web.domain.dto.ContactDetailsInfoDto;
import za.co.multishare.web.domain.dto.UserDetailsDto;
import za.co.multishare.web.domain.entity.ContactInfo;
import za.co.multishare.web.domain.entity.UserInfo;
import za.co.multishare.web.domain.entity.UserInfoDetail;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    public List<UserDetailsDto> searchForUsers(final String searchQuery) {
        List<UserDetailsDto> userDetailsDtoList = new ArrayList<>();

        List<ContactInfo> contactInfoList = contactInfoService.search(searchQuery);

        contactInfoList.forEach(contactInfo -> {
            final UserInfo userInfo = contactInfo.getUserInfo();
            final UserInfoDetail userInfoDetail = userDetailService.findActive(userInfo.getUserInfoId());
            userDetailsDtoList.add(new UserDetailsDto(userInfo.getUserInfoId(),
                    null, userInfoDetail.getName(), userInfoDetail.getSurname(),
                    userInfoDetail.getGender(), null, null));
        });

        List<UserInfoDetail> userInfoDetailList = userDetailService.search(searchQuery);

        userInfoDetailList.forEach(userInfoDetail -> {
            userDetailsDtoList.add(new UserDetailsDto(userInfoDetail.getUserInfo().getUserInfoId(),
                    null, userInfoDetail.getName(), userInfoDetail.getSurname(),
                    userInfoDetail.getGender(), null, null));
        });

        return userDetailsDtoList;
    }

    @Override
    public UserInfo searchForUserByContact(String contact) {
        return contactInfoService.findActive(contact).getUserInfo();
    }

    @Override
    @Transactional
    public UserDetailsDto updateUserDetails(UserDetailsDto userDetailsDto) {

        final List<ContactInfo> contactInfoList = contactInfoService.findActive(userDetailsDto.getId());

        contactInfoList.forEach(contactInfo -> {
            final String updateContact = userDetailsDto.getContactDetailsInfoDtoList()
                    .stream()
                    .filter(contactDetailsInfoDto -> contactInfo.getContactType()
                            .equalsIgnoreCase(contactDetailsInfoDto.getContactType()))
                    .findAny().orElseThrow(() -> new RuntimeException("Failed to resolve contact info"))
                    .getContact();
            contactInfo.setContact(updateContact);
        });

        final List<ContactInfo> contactInfoListResults = contactInfoService.updateContactInfos(contactInfoList);

        UserInfoDetail userInfoDetail = userDetailService.findActive(userDetailsDto.getId());
        userInfoDetail.setTitle(userDetailsDto.getTitle());
        userInfoDetail.setName(userDetailsDto.getName());
        userInfoDetail.setSurname(userDetailsDto.getSurname());
        userInfoDetail.setLegalIdentityNumber(userDetailsDto.getLegalIdentityNumber());
        userInfoDetail.setGender(userDetailsDto.getGender());

         userInfoDetail = userDetailService.updateUserInfoDetail(Collections.singletonList(userInfoDetail)).get(0);

        userDetailsDto.setTitle(userInfoDetail.getTitle());
        userDetailsDto.setName(userInfoDetail.getName());
        userDetailsDto.setSurname(userInfoDetail.getSurname());
        userDetailsDto.setLegalIdentityNumber(userDetailsDto.getLegalIdentityNumber());
        userDetailsDto.setGender(userInfoDetail.getGender());

        userDetailsDto.getContactDetailsInfoDtoList().forEach(contactDetailsInfoDto -> {
            final String updateContact = contactInfoListResults
                    .stream()
                    .filter(contactInfo -> contactInfo
                            .getContactType()
                            .equalsIgnoreCase(contactDetailsInfoDto.getContactType()))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("Failed to resolve contact info dto"))
                    .getContact();
            contactDetailsInfoDto.setContact(updateContact);
        });

        return userDetailsDto;
    }
}
