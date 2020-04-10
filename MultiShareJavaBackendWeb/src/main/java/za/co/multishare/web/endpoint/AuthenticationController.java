package za.co.multishare.web.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.multishare.business.service.UserInfoDetailsRetrievalService;
import za.co.multishare.web.entity.AuthenticationRequest;
import za.co.multishare.web.security.service.JwtTokenProvider;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService;

    @Autowired
    public AuthenticationController(final AuthenticationManager authenticationManager,
                                    final JwtTokenProvider jwtTokenProvider,
                                    final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userInfoDetailsRetrievalService = userInfoDetailsRetrievalService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Map<Object, Object>> signIn(@RequestBody final AuthenticationRequest authenticationRequest) {
        return null;
    }

}
