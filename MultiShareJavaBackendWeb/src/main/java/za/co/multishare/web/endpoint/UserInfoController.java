package za.co.multishare.web.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.multishare.business.service.UserInfoDetailsRetrievalService;
import za.co.multishare.domain.dto.UserDetailsDto;

@RestController
@RequestMapping("/api/user-info")
public class UserInfoController {

    private final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService;

    @Autowired
    public UserInfoController(final UserInfoDetailsRetrievalService userInfoDetailsRetrievalService) {
        this.userInfoDetailsRetrievalService = userInfoDetailsRetrievalService;
    }

    @GetMapping("/get/details/{userId}")
    public ResponseEntity<UserDetailsDto> getUserDetails(@PathVariable final Long userId) {
        final UserDetailsDto userDetailsDto = userInfoDetailsRetrievalService.getUserDetails(userId);

        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
    }
}
