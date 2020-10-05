package za.co.multishare.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.multishare.domain.entity.UserRoleInfo;
import za.co.multishare.repository.UserRoleInfoRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/userRoles/")
public class UserRoleController {

    private final UserRoleInfoRepository userRoleInfoRepository;

    @Autowired
    public UserRoleController(final UserRoleInfoRepository userRoleInfoRepository) { this.userRoleInfoRepository = userRoleInfoRepository; }

    @GetMapping("get/{userId}")
    public ResponseEntity<List<String>> getUserRoles(@PathVariable final Long userId) {
       final List<UserRoleInfo> userRoleInfoList = userRoleInfoRepository.findByUserInfoUserInfoIdAndRecordValidToDateIsNull(userId);

        return new ResponseEntity<>(userRoleInfoList
                .stream()
                .map(UserRoleInfo::getUserRole)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}
