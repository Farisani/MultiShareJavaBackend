package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.dto.AdminUserDetailsDto;
import za.co.multishare.domain.entity.ContactInfo;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.domain.entity.UserInfoDetail;
import za.co.multishare.repository.ContactInfoRepository;
import za.co.multishare.repository.UserInfoDetailRepository;
import za.co.multishare.repository.UserInfoRepository;
import za.co.multishare.service.AdminService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoDetailRepository userInfoDetailRepository;
    private final ContactInfoRepository contactInfoRepository;

    @Autowired
    public AdminServiceImpl(final UserInfoRepository userInfoRepository,
                            final UserInfoDetailRepository userInfoDetailRepository,
                            final ContactInfoRepository contactInfoRepository) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoDetailRepository = userInfoDetailRepository;
        this.contactInfoRepository = contactInfoRepository;
    }

    @Override
    public List<AdminUserDetailsDto> searchForUsers(String searchCriteria,
                                                    String searchQuery,
                                                    Integer pageNumber,
                                                    Integer pageSize) {

        final List<UserInfo> userInfoList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if ("contact".equalsIgnoreCase(searchCriteria)) {
            userInfoList.addAll(contactInfoRepository
                    .findByContactContainingAndRecordValidToDateIsNull(searchQuery, pageable)
                    .stream().map(ContactInfo::getUserInfo).collect(Collectors.toList()));
        } else {
            userInfoList.addAll(userInfoDetailRepository
                    .findByNameContainingOrSurnameContainingAndRecordValidToRecordIsNull(searchQuery, searchQuery, pageable)
                    .stream().map(UserInfoDetail::getUserInfo).collect(Collectors.toList()));
        }

        final List<AdminUserDetailsDto> adminUserDetailsDtoList = userInfoList.stream().map(userInfo -> {
            final UserInfoDetail userInfoDetail = userInfoDetailRepository
                    .findByUserInfoUserInfoIdAndRecordValidToRecordIsNull(userInfo.getUserInfoId());
            return new AdminUserDetailsDto(userInfo.getUserInfoId(),
                    userInfoDetail.getSurname(),
                    userInfoDetail.getName(),
                    userInfo.getRecordValidFromDate());
        }).collect(Collectors.toList());

        return adminUserDetailsDtoList;
    }

    @Override
    public List<AdminUserDetailsDto> searchByRegistrationDates(LocalDateTime startDate,
                                                               LocalDateTime endDate,
                                                               Integer pageNumber,
                                                               Integer pageSize) {

        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
        final List<UserInfo> userInfoList = userInfoRepository.findUserInfoByRecordValidFromDateBetweenStartDateAndEndDate(startDate,
                endDate, pageable);

        final List<AdminUserDetailsDto> adminUserDetailsDtoList = userInfoList.stream().map(userInfo -> {
            final UserInfoDetail userInfoDetail = userInfoDetailRepository
                    .findByUserInfoUserInfoIdAndRecordValidToRecordIsNull(userInfo.getUserInfoId());
            return new AdminUserDetailsDto(userInfo.getUserInfoId(),
                    userInfoDetail.getSurname(),
                    userInfoDetail.getName(),
                    userInfo.getRecordValidFromDate());
        }).collect(Collectors.toList());

        return adminUserDetailsDtoList;
    }

    @Override
    public List<AdminUserDetailsDto> getAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        final List<UserInfo> userInfoList = userInfoRepository.findAll(pageable).toList();


        final List<AdminUserDetailsDto> adminUserDetailsDtoList = userInfoList.stream().map(userInfo -> {
            final UserInfoDetail userInfoDetail = userInfoDetailRepository
                    .findByUserInfoUserInfoIdAndRecordValidToRecordIsNull(userInfo.getUserInfoId());
            return new AdminUserDetailsDto(userInfo.getUserInfoId(),
                    userInfoDetail.getSurname(),
                    userInfoDetail.getName(),
                    userInfo.getRecordValidFromDate());
        }).collect(Collectors.toList());

        return adminUserDetailsDtoList;
    }

    @Override
    public Integer getTotalNumberOfUsers() {
        return userInfoRepository.findAll().size();
    }
}
