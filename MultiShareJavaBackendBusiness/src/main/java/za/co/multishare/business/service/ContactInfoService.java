package za.co.multishare.business.service;

import za.co.multishare.domain.dto.ContactInfoDto;
import za.co.multishare.domain.entity.ContactInfo;
import za.co.multishare.domain.entity.UserInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ContactInfoService {

    /**
     * Retrieves all contact info's for input list of user info ID's, it does the limiting by using page number and size
     * @param userInfoIdList - list of user info ID's whose contact info are to be retrieved
     * @param pageNumber - page number of the collection to be returned
     * @param pageSize - page size for each page
     * @return a map with each id and its list of contact infos
     */
    Map<Long, List<ContactInfo>> findAll(List<Long> userInfoIdList,
                                         Integer pageNumber,
                                         Integer pageSize);

    List<ContactInfo> findActive(Long userInfoId);

    /**
     * Creates contact info's based on the
     * @param contactInfoDtoList - contact info dto list of user infos to be created
     * @return a list of the newly created list of user infos
     */
    List<ContactInfo> createContactInfo(List<ContactInfoDto> contactInfoDtoList,
                                        LocalDateTime recordValidFromDate,
                                        LocalDateTime recordValidToDate,
                                        UserInfo userInfo);

    /**
     * Deletes the list of contact info whose Id's are passed in the input list
     * @param contactInfoIdList - a list of contact info id's
     */
    void deleteContactInfo(List<Long> contactInfoIdList);


    List<ContactInfo> search(String searchQuery);
}
