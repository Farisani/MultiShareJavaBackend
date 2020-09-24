package za.co.multishare.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.multishare.service.UserInfoDetailsRetrievalService;
import za.co.multishare.domain.dto.UserDetailsDto;

import java.util.List;

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

    @GetMapping("/search")
    public ResponseEntity<List<UserDetailsDto>> searchForUsers(@RequestParam final String searchQuery) {
        final List<UserDetailsDto> userDetailsDtoList = userInfoDetailsRetrievalService.searchForUsers(searchQuery);

        return new ResponseEntity<>(userDetailsDtoList, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<UserDetailsDto> updateUserDetails(@RequestBody final UserDetailsDto userDetailsDto) {
        final UserDetailsDto userDetailsDtoResults = userInfoDetailsRetrievalService.updateUserDetails(userDetailsDto);

        return new ResponseEntity<>(userDetailsDtoResults, HttpStatus.OK);
    }
}
