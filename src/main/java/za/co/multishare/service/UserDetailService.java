package za.co.multishare.service;

import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.domain.entity.UserInfoDetail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface UserDetailService {

    /**
     * Finds all the user info details for the passed input list of user infos
     * @param userInfIdList
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Map<Long, List<UserInfoDetail>> findAll(List<Long> userInfIdList, Integer pageNumber, Integer pageSize);

    UserInfoDetail findActive(Long userInfoId);

    /**
     * Creates a user details info
     * @param title
     * @param surname
     * @param name
     * @param gender
     * @param legalIdentityNumber
     * @param recordValidFromDate
     * @param recordValidToDate
     * @param userInfo
     * @return
     */
    UserInfoDetail createUserInfoDetail(String title,
                                        String surname,
                                        String name,
                                        String gender,
                                        String legalIdentityNumber,
                                        LocalDateTime recordValidFromDate,
                                        LocalDateTime recordValidToDate,
                                        UserInfo userInfo);

    /**
     * Deletes the user infos for the list of user info id's passed
     * @param userInfoId
     */
    void deleteUserInfoDetail(List<Long> userInfoId);

    /**
     * Updates the list of user info details passed
     * @param userInfoDetailList
     * @return
     */
    List<UserInfoDetail> updateUserInfoDetail(List<UserInfoDetail> userInfoDetailList);

    List<UserInfoDetail> search(String searchQuery);
}
