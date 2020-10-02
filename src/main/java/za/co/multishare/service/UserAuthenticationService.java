package za.co.multishare.service;

import za.co.multishare.domain.dto.UserLoginInternalDto;

import java.util.Map;

public interface UserAuthenticationService {

    UserLoginInternalDto getUserLoginInformation(String username);

    Map<String, String> authenticateUser(String username, String password);
}
