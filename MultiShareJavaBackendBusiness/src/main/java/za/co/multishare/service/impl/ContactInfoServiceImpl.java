package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.multishare.repository.repository.ContactInfoRepository;
import za.co.multishare.service.ContactInfoService;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {

    private final ContactInfoRepository contactInfoRepository;

    @Autowired
    public ContactInfoServiceImpl(final ContactInfoRepository contactInfoRepository) {
        this.contactInfoRepository = contactInfoRepository;
    }
}
