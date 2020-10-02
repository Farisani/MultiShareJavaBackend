package za.co.multishare.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.multishare.domain.dto.LoginDto;
import za.co.multishare.service.UserAuthenticationService;

import java.util.Map;

@RestController
@RequestMapping("/api/security/")
public class AuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public AuthenticationController(final UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody final LoginDto loginDto) {
        final Map<String, String> authenticationResults = userAuthenticationService
                .authenticateUser(loginDto.getUsername(), loginDto.getPassword());
       return new ResponseEntity<>(authenticationResults, HttpStatus.OK);
    }
}
