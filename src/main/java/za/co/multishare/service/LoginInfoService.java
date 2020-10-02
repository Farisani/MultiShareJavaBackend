package za.co.multishare.service;

import za.co.multishare.domain.entity.LoginInfo;
import za.co.multishare.domain.entity.UserInfo;

import java.time.LocalDateTime;

public interface LoginInfoService {

    /**
     * Creates a login info record from the date passed
     * @param password - user's password
     * @param recordValidFromDate - start date for password to be valid
     * @param recordValidToDate - end date for password to be valid
     * @param userInfo - user info whom the login info belongs to
     * @return a new instance of LoginInfo
     */
    LoginInfo createLoginInfo(String password,
                              LocalDateTime recordValidFromDate,
                              LocalDateTime recordValidToDate,
                              UserInfo userInfo);

    /**
     * Deletes the users
     * @param loginInfoId - login info id for record to be deleted
     * @param recordValidToDate - date for deleting record
     */
    void deleteLoginInfo(Long loginInfoId, LocalDateTime recordValidToDate);

    LoginInfo getLoginInfo(Long userInfoId);
}
