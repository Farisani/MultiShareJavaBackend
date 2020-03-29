package za.co.multishare.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.dto.ContactInfoDto;
import za.co.multishare.repository.repository.ContactInfoRepository;
import za.co.multishare.business.service.UserInfoService;
import za.co.multishare.domain.entity.ContactInfo;
import za.co.multishare.business.service.ContactInfoService;

import java.util.List;
import java.util.Map;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {

    private final ContactInfoRepository contactInfoRepository;
    private final UserInfoService userInfoService;

    @Autowired
    public ContactInfoServiceImpl(final ContactInfoRepository contactInfoRepository,
                                  final UserInfoService userInfoService) {
        this.contactInfoRepository = contactInfoRepository;
        this.userInfoService = userInfoService;
    }

    @Override
    public Map<Long, List<ContactInfo>> findAll(final List<Long> userInfoIdList,
                                                final Integer pageNumber,
                                                final Integer pageSize) {
        return null;
    }

    @Override
    public List<ContactInfo> createContactInfo(final List<ContactInfoDto> contactInfoDtoList) {
        return null;
    }

    @Override
    public void deleteContactInfo(final List<Long> contactInfoIdList) {

    }
}
