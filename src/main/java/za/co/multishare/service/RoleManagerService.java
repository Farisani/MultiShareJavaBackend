package za.co.multishare.service;

import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.domain.entity.UserRoleInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface RoleManagerService {

    List<UserRoleInfo> createRoleInfo(List<String> roleList,
                                      LocalDateTime recordValidFromDate,
                                      LocalDateTime recordValidToDate,
                                      UserInfo userInfo);

    List<UserRoleInfo> getActiveUserRoles(Long userInfoId);
}
