package za.co.multishare.business.service;

import za.co.multishare.domain.dto.UserDetailsDto;

public interface UserInfoDetailsRetrievalService {

    /**
     * Gets user details for the passed user id
     * @param userId - user Id whose user information is being retrieved
     * @return instance of user details dto
     */
    UserDetailsDto getUserDetails(Long userId);
}
