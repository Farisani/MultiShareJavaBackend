package za.co.multishare.web.service;

import za.co.multishare.web.domain.dto.UserInfoDto;
import za.co.multishare.web.domain.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    /**
     * finds all the user info's in the database based on the page number and page size
     * @param pageNumber - page of user infos to be retrieved
     * @param pageSize - page size of user info's to be retrieved
     * @return a list of user info;s
     */
    List<UserInfo> findAll(Integer pageNumber, Integer pageSize);

    /**
     * Creates user info's from the list of user info dto's. If an exception occurs for one of them
     * the whole service call fails.
     * @param userInfoDtoList user info dto list
     * @return a list of user info's
     */
    List<UserInfo> createUserInfo(List<UserInfoDto> userInfoDtoList);

    /**
     * Deletes the user info's of the list passed. If an exception occurs for one of them
     * the whole service call fails
     * @param userInfoIdList a list of user info's ID
     */
    void deleteUserInfo(List<Long> userInfoIdList);

    /**
     * Updates each and every user info in the user info list input. If an exception occurs
     * for one them. the whole service call fails.
     * @param userInfoList a list of user infos
     * @return updated list of user info's
     */
    List<UserInfo> updateUserInfo(List<UserInfo> userInfoList);


    UserInfo findById(Long userInfoId);

}
