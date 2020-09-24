package za.co.multishare.web.service;

import za.co.multishare.web.domain.entity.UserInfo;
import za.co.multishare.web.domain.entity.UserRoleInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface RoleManagerService {

    List<UserRoleInfo> createRoleInfo(List<String> roleList,
                                      LocalDateTime recordValidFromDate,
                                      LocalDateTime recordValidToDate,
                                      UserInfo userInfo);

    List<UserRoleInfo> getActiveUserRoles(Long userInfoId);
}
