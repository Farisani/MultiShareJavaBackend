package za.co.multishare.business.service;

import za.co.multishare.domain.dto.RegistrationDto;

import java.time.LocalDateTime;

public interface RegistrationService {

    /**
     * performs registration for a new member
     * @param registrationDto - dto with member's data
     * @param recordValidFromDate - start date for registration to be valid
     * @param recordValidToDate - end date for registration to be valid
     */
    Long registerUser(RegistrationDto registrationDto,
                      LocalDateTime recordValidFromDate,
                      LocalDateTime recordValidToDate);
}
