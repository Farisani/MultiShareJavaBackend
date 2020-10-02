package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.domain.entity.UserInfoDetail;
import za.co.multishare.repository.UserInfoDetailRepository;
import za.co.multishare.service.UserDetailService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    private final UserInfoDetailRepository userInfoDetailRepository;

    @Autowired
    public UserDetailServiceImpl(final UserInfoDetailRepository userInfoDetailRepository) {
        this.userInfoDetailRepository = userInfoDetailRepository;
    }

    @Override
    public UserInfoDetail findActive(final Long userInfoId) {
        final UserInfoDetail userInfoDetail = userInfoDetailRepository.findByUserInfoUserInfoIdAndRecordValidToRecordIsNull(userInfoId);

        return userInfoDetail;
    }

    @Override
    public Map<Long, List<UserInfoDetail>> findAll(final List<Long> userInfIdList,
                                                   final Integer pageNumber,
                                                   final Integer pageSize) {
        final Pageable page = PageRequest.of(pageNumber, pageSize);
        final Map<Long, List<UserInfoDetail>> userDetailsInfoMap = new HashMap<>();

        userInfIdList.forEach(userInfoId -> {
            final List<UserInfoDetail> userInfoDetailList = userInfoDetailRepository
                    .findByUserInfoUserInfoId(userInfoId, page)
                    .toList();
            userDetailsInfoMap.put(userInfoId, userInfoDetailList);
        });

        return userDetailsInfoMap;
    }

    @Override
    public UserInfoDetail createUserInfoDetail(final String title,
                                               final String surname,
                                               final String name,
                                               final String gender,
                                               final String legalIdentityNumber,
                                               final LocalDateTime recordValidFromDate,
                                               final LocalDateTime recordValidToDate,
                                               final UserInfo userInfo) {
        final UserInfoDetail userInfoDetail = new UserInfoDetail(null, title, name, surname, gender,
                legalIdentityNumber, recordValidFromDate, recordValidToDate, userInfo);

        return userInfoDetailRepository.save(userInfoDetail);
    }

    @Override
    public void deleteUserInfoDetail(final List<Long> userInfoIdList) {

        final List<UserInfoDetail> userInfoDetailList = new ArrayList<>();

        userInfoIdList.forEach(userInfoId -> {
            userInfoDetailList.add(userInfoDetailRepository
                    .findByUserInfoUserInfoIdAndRecordValidToRecordIsNull(userInfoId));
        });

        userInfoDetailRepository.deleteAll(userInfoDetailList);
    }

    @Override
    public List<UserInfoDetail> updateUserInfoDetail(final List<UserInfoDetail> userInfoDetailList) {
        return userInfoDetailRepository.saveAll(userInfoDetailList);
    }

    @Override
    public List<UserInfoDetail> search(String searchQuery) {
        return userInfoDetailRepository.findByNameContainingOrSurnameContainingAndRecordValidToRecordIsNull(searchQuery, searchQuery);
    }
}
