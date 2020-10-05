package za.co.multishare.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.multishare.domain.dto.LoginDto;
import za.co.multishare.domain.entity.UserRoleInfo;
import za.co.multishare.repository.UserRoleInfoRepository;
import za.co.multishare.service.UserAuthenticationService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/security/")
public class AuthenticationController {

    private final UserAuthenticationService userAuthenticationService;
    private final UserRoleInfoRepository userRoleInfoRepository;

    @Autowired
    public AuthenticationController(final UserAuthenticationService userAuthenticationService,
                                    final UserRoleInfoRepository userRoleInfoRepository) {
        this.userAuthenticationService = userAuthenticationService;
        this.userRoleInfoRepository = userRoleInfoRepository;
    }

    @PostMapping("authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody final LoginDto loginDto) {
        final Map<String, String> authenticationResults = userAuthenticationService
                .authenticateUser(loginDto.getUsername(), loginDto.getPassword());
        final Long userInfoId = Long.valueOf(authenticationResults.get("userId"));
        final List<UserRoleInfo> userRoleInfoList = userRoleInfoRepository.findByUserInfoUserInfoIdAndRecordValidToDateIsNull(userInfoId);
        final List<String> rolesList = userRoleInfoList.stream().map(UserRoleInfo::getUserRole).collect(Collectors.toList());
        authenticationResults.put("roles", rolesList.toString().substring(1, rolesList.toString().length() -1).replace(" ", ""));
       return new ResponseEntity<>(authenticationResults, HttpStatus.OK);
    }
}
