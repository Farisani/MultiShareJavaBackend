package za.co.multishare.web.service;

import za.co.multishare.web.domain.dto.UserDetailsDto;
import za.co.multishare.web.domain.entity.UserInfo;

import java.util.List;

public interface UserInfoDetailsRetrievalService {

    /**
     * Gets user details for the passed user id
     * @param userId - user Id whose user information is being retrieved
     * @return instance of user details dto
     */
    UserDetailsDto getUserDetails(Long userId);

    List<UserDetailsDto> searchForUsers(String searchQuery);

    UserInfo searchForUserByContact(String contact);

    UserDetailsDto updateUserDetails(UserDetailsDto userDetailsDto);
}
