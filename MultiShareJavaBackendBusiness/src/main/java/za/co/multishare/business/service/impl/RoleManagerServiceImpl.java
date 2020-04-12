package za.co.multishare.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.business.service.RoleManagerService;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.domain.entity.UserRoleInfo;
import za.co.multishare.repository.repository.UserRoleInfoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleManagerServiceImpl implements RoleManagerService {

    private final UserRoleInfoRepository userRoleInfoRepository;

    @Autowired
    public RoleManagerServiceImpl(final UserRoleInfoRepository userRoleInfoRepository) {
        this.userRoleInfoRepository = userRoleInfoRepository;
    }

    @Override
    public List<UserRoleInfo> createRoleInfo(final List<String> roleList,
                                             final LocalDateTime recordValidFromDate,
                                             final LocalDateTime recordValidToDate,
                                             final UserInfo userInfo) {

        final List<UserRoleInfo> userRoleInfoList = new ArrayList<>();

        roleList.forEach(role -> userRoleInfoList.add(
                new UserRoleInfo(null,
                        role,
                        recordValidFromDate,
                        recordValidToDate,
                        userInfo)));

        return userRoleInfoRepository.saveAll(userRoleInfoList);
    }

    @Override
    public List<UserRoleInfo> getActiveUserRoles(final Long userInfoId) {
        return userRoleInfoRepository
                .findByUserInfoUserInfoIdAndRecordValidToDateIsNull(userInfoId);
    }
}
