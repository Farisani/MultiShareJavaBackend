package za.co.multishare.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.multishare.domain.dto.RegistrationDto;
import za.co.multishare.service.RegistrationService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/")
    public ResponseEntity<Long> register(@RequestBody final RegistrationDto registrationDto) {
        final Long userInfoId = registrationService.registerUser(registrationDto, LocalDateTime.now(), null);
        return new ResponseEntity<>(userInfoId, HttpStatus.OK);
    }
}
