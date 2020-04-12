package za.co.multishare.web.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.multishare.domain.dto.AuthenticationRequest;
import za.co.multishare.web.security.service.AuthenticationService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticateService;

    @Autowired
    public AuthenticationController(final AuthenticationService authenticateService) {
       this.authenticateService = authenticateService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Map<Object, Object>> signIn(@RequestBody final AuthenticationRequest authenticationRequest) {

        Map<Object, Object> authInfo = authenticateService
                .authenticate(authenticationRequest.getUsername(),
                        authenticationRequest.getUsername());

        return new ResponseEntity<>(authInfo, HttpStatus.OK);
    }

}
