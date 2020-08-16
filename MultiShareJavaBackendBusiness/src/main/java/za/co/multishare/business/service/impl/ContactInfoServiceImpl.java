package za.co.multishare.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.dto.ContactInfoDto;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.repository.repository.ContactInfoRepository;
import za.co.multishare.domain.entity.ContactInfo;
import za.co.multishare.business.service.ContactInfoService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {

    private final ContactInfoRepository contactInfoRepository;

    @Autowired
    public ContactInfoServiceImpl(final ContactInfoRepository contactInfoRepository) {
        this.contactInfoRepository = contactInfoRepository;
    }

    @Override
    public Map<Long, List<ContactInfo>> findAll(final List<Long> userInfoIdList,
                                                final Integer pageNumber,
                                                final Integer pageSize) {

        final Pageable page = PageRequest.of(pageNumber, pageSize);
        final Map<Long, List<ContactInfo>> contactInfoMap = new HashMap<>();

        userInfoIdList.forEach(userInfoId -> {
            final List<ContactInfo> contactInfoList = contactInfoRepository
                    .findAllByUserInfoUserInfoId(userInfoId, page).toList();
            contactInfoMap.put(userInfoId, contactInfoList);
        });

        return contactInfoMap;
    }

    @Override
    public List<ContactInfo> findActive(Long userInfoId) {
        return contactInfoRepository.findAllByUserInfoUserInfoIdAndRecordValidToDateIsNull(userInfoId);
    }

    @Override
    public List<ContactInfo> createContactInfo(final List<ContactInfoDto> contactInfoDtoList,
                                               final LocalDateTime recordValidFromDate,
                                               final LocalDateTime recordValidToDate,
                                               final UserInfo userInfo) {

        final List<ContactInfo> contactInfoList = new ArrayList<>();

        contactInfoDtoList.forEach(contactInfoDto ->
            contactInfoList.add(new ContactInfo(null, contactInfoDto.getContact(),
                    contactInfoDto.getContactType(), recordValidFromDate, recordValidToDate, userInfo)));

        return contactInfoRepository.saveAll(contactInfoList);
    }

    @Override
    public void deleteContactInfo(final List<Long> contactInfoIdList) {

    }

    @Override
    public List<ContactInfo> search(final String searchQuery) {
        return contactInfoRepository.findByContactContainingAndRecordValidToDateIsNull(searchQuery);
    }

    @Override
    public ContactInfo findActive(String searchQuery) {
        return contactInfoRepository.findByContactAndRecordValidToDateIsNull(searchQuery);
    }

    @Override
    public List<ContactInfo> updateContactInfos(List<ContactInfo> contactInfoList) {
        return contactInfoRepository.saveAll(contactInfoList);
    }
}
