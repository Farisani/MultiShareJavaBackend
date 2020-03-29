package za.co.multishare.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.dto.UserInfoDto;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.repository.repository.UserInfoRepository;
import za.co.multishare.business.service.UserInfoService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(final UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public List<UserInfo> findAll(Integer pageNumber, Integer pageSize) {
        final Pageable page = PageRequest.of(pageNumber, pageSize);
        return  userInfoRepository.findAll(page).toList();
    }

    @Override
    public List<UserInfo> createUserInfo(List<UserInfoDto> userInfoDtoList) {

        final List<UserInfo> userInfoList = new ArrayList<>();

        userInfoDtoList.forEach(userInfoDto ->
            userInfoList.add(new UserInfo(null,
                    userInfoDto.getRecordValidFromDate(),
                    userInfoDto.getRecordValidToDate())));

        return userInfoRepository.saveAll(userInfoList);
    }

    @Override
    public void deleteUserInfo(List<Long> userInfoIdList) {
        final List<UserInfo> userInfoList = userInfoRepository.findAllById(userInfoIdList);
        userInfoRepository.deleteAll(userInfoList);
    }

    @Override
    public List<UserInfo> updateUserInfo(List<UserInfo> userInfoList) {
        return userInfoRepository.saveAll(userInfoList);
    }
}
